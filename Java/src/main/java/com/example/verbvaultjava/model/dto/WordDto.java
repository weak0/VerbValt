package com.example.verbvaultjava.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WordDto {
    private Long wordId;
    private String foreignWord;
    private String translation;
}
