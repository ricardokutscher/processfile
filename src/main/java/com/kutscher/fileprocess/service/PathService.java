package com.kutscher.fileprocess.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PathService {

    @Value("${file.input}")
    private String input;

    @Value("${file.output}")
    private String output;

    public Path getOutputDirectory() {
        Path outputDirectory = Paths.get(System.getProperty("user.home") + output);
        if (!Files.exists(outputDirectory)) {
            createPath(outputDirectory);
        }
        return outputDirectory;
    }

    public Path getInputDirectory() {
        Path inputDirectory = Paths.get(System.getProperty("user.home") + input);
        if (!Files.exists(inputDirectory)) {
            createPath(inputDirectory);
        }
        return inputDirectory;
    }

    public static void createPath(Path path){
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            System.out.println("Error: Please check if the application has permission to create the path.");
        }
    }
}
