package com.example.verbvaultjava.model.dto;

import lombok.Data;

@Data
public class UserDto {
    private String userName;
    private String password;
    private String email;
    private String roleName;
}
