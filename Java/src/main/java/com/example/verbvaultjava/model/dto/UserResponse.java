package com.example.verbvaultjava.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
@Data
@Builder
public class UserResponse {
    private String username;
    private String email;
    private Set<WordDto> wordDto;
    //private Course course;
}
