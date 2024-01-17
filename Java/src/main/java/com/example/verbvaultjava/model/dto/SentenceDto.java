package com.example.verbvaultjava.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SentenceDto {
    private Long sentenceId;
    private String foreignSentence;
    private String translation;
}
