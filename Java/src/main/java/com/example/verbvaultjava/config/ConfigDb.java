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
        String csv = System.getProperty("user.dir")+"\\Java\\CSV";
        dataLoadingService.loadSentences(csv + "\\zdania A1.csv", "a1");
        dataLoadingService.loadWords(csv + "\\słowka A1.csv", "a1");
        dataLoadingService.loadSentences(csv + "\\zdania B2.csv", "b2");
        dataLoadingService.loadWords(csv + "\\slowka B2.csv", "b2");

    }
}
