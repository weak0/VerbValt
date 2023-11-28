package com.example.verbvaultjava.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private String username;
    private String email;
    private String roleName;
    private List<String>courses;
    }
