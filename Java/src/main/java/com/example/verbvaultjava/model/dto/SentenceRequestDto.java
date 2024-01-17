package com.example.verbvaultjava.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SentenceRequestDto {
    private Long sentenceId;
    private Long userId;
    private String sentence;
}
