package com.example.verbvaultjava.config;

import com.example.verbvaultjava.service.DataLoadingService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
@Getter
public class ConfigDb implements CommandLineRunner {
    private final DataLoadingService dataLoadingService;

    @Override
    public void run(String... args) throws Exception {
        Path csv = Paths.get(System.getProperty("user.dir"))
                .resolve("CSV");
        dataLoadingService.loadSentences(csv.toAbsolutePath() + "\\zdania A1.csv", "a1");
        dataLoadingService.loadWords(csv.toAbsolutePath() + "\\słowka A1.csv", "a1");
        dataLoadingService.loadSentences(csv.toAbsolutePath() + "\\zdania B2.csv", "b2");
        dataLoadingService.loadWords(csv.toAbsolutePath() + "\\slowka B2.csv", "b2");

    }
}
