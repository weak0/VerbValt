package com.example.verbvaultjava.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseWordResponseDto {
    private Long id;
    private String status;
}
