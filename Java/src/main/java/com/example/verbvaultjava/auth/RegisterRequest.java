package com.example.verbvaultjava.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String nickName;
    private String firstName;
    private String email;
    private String password;
    private String role;
}
