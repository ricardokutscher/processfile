package com.kutscher.fileprocess.service;

import com.kutscher.fileprocess.dto.factory.LineFileFactory;
import com.kutscher.fileprocess.exception.InvalidLineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Object> process(String fileName) throws Exception {
        return fileService.readLinesFile(fileName)
                .map(line -> parseLine(line))
                .collect(Collectors.toList());
    }

    private Object parseLine(String line) throws InvalidLineException {
        return lineFileFactory.get()
                .filter(o -> o.isValid(line))
                .findFirst()
                .flatMap(x -> x.parse(line))
                .orElseThrow(() -> new InvalidLineException("File has invalid line. Please correct line with: " + line));
    }

}
