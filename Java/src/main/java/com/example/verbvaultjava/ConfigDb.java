package com.example.verbvaultjava;

import com.example.verbvaultjava.service.DataLoadingService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class ConfigDb implements CommandLineRunner {
    private final DataLoadingService dataLoadingService;
    @Value("${path.Csv}")
    private String path;
    @Override
    public void run(String... args) throws Exception {
        dataLoadingService.loadSentences(getPath()+"zdania A1.csv","a1");
        dataLoadingService.loadWords(getPath()+"słowka A1.csv","a1");
        dataLoadingService.loadSentences(getPath()+"zdania B2.csv","b2");
        dataLoadingService.loadWords(getPath()+"slowka B2.csv","b2");

    }
}
