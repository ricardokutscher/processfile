package com.kutscher.fileprocess.watcher;

import com.kutscher.fileprocess.service.OutputFileService;
import com.kutscher.fileprocess.utils.LineParser;
import com.kutscher.fileprocess.service.InputFileService;
import com.kutscher.fileprocess.service.PathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.regex.Matcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@Component
public class FileWatcher {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PathService pathService;

    @Autowired
    private InputFileService inputFileService;

    @Autowired
    private OutputFileService outputFileService;

    @Autowired
    private LineParser lineParser;

    @Value("${file.valid-format}")
    private String validFormats;

    @PostConstruct
    public void watcher() throws IOException, InterruptedException {
        WatchService watcher = registerWatcher();

        while (true) {
            WatchKey key = watcher.take();
            key.pollEvents().stream()
                .filter(this::validateFile)
                .forEach(this::proccessFile);
            key.reset();
        }
    }

    public WatchService registerWatcher() throws IOException {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        Path dir = pathService.getInputDirectory();
        dir.register(watcher, ENTRY_CREATE);
        return watcher;
    }

    private Boolean validateFile(WatchEvent event) {
        Matcher result = lineParser.getTokens(event.context().toString(), "([\\s\\S]+)("+validFormats+")$");
        if (event.kind() == ENTRY_CREATE && result.find()) {
            logger.info("Valid file to process, file: " + event.context().toString());
            return true;
        }
        logger.info("Invalid file to process, file: " + event.context().toString());
        return false;
    }

    private void proccessFile(WatchEvent watchEvent){
        try {
            List<Object> lines = inputFileService.process(watchEvent.context().toString());
            outputFileService.process(lines, watchEvent.context().toString());
        } catch (IOException e) {
            logger.error("Could not process file. Error: ", e.getStackTrace().toString());
        }
    }
}
