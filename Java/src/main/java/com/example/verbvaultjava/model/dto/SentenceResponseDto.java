package com.example.verbvaultjava.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SentenceResponseDto {
    private Long sentenceId;
    private String status;
}
