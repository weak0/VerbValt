package com.example.verbvaultjava.model.dto;

import com.example.verbvaultjava.model.Course;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
@Builder
public class UserResponse {
    private String username;
    private String email;
    private Set<WorldDto> worlds=new HashSet<>();
    //private Course course;
}
