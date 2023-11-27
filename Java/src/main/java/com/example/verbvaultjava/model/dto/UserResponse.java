package com.example.verbvaultjava.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;
@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Set<WordDto> wordDto;
    private List<String>courses;
}
