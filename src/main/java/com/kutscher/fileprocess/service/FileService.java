package com.kutscher.fileprocess.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileService {

    public FileService(PathService pathService) {
        this.pathService = pathService;
    }

    private PathService pathService;

    public Stream<String> readLinesFile(String fileName) throws IOException { return Files.lines(Paths.get(pathService.getInputDirectory() + "/" + fileName));
    }

    public void generateOutputFile(List<String> lines, String fileName) throws IOException {
        Files.write(Paths.get(pathService.getOutputDirectory() + "/" + fileName + ".done"), lines, Charset.defaultCharset());
    }
}
