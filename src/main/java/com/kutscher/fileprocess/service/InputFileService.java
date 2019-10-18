package com.kutscher.fileprocess.service;

import com.kutscher.fileprocess.dto.factory.LineFileFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class InputFileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private PathService pathService;
    private LineFileFactory lineFileFactory;
    private FileService fileService;

    public InputFileService(PathService pathService, LineFileFactory lineFileFactory, FileService fileService) {
        this.pathService = pathService;
        this.lineFileFactory = lineFileFactory;
        this.fileService = fileService;
    }

    public List<Object> process(String fileName) throws IOException {
        logger.info("Processing file: " + fileName);
        return processInput(fileName);
    }
    
    public List<Object> processInput(String fileName) throws IOException {
        List<Object> lines = new ArrayList<Object>();
        fileService.readLinesFile(fileName)
            .forEach(line -> {
                logger.info("Processing line: " + line);
                lineFileFactory.get()
                    .filter(o -> o.isValid(line))
                    .findAny()
                    .flatMap(o -> o.parse(line))
                    .ifPresent(x -> lines.add(x));
            });
        return lines;
    }



}
