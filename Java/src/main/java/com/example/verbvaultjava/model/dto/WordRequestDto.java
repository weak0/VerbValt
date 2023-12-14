package com.example.verbvaultjava.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WordRequestDto {
    private Long wordId;
    private Long userId;
    private String word;
    }
