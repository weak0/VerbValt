package com.example.verbvaultjava.service;

import com.example.verbvaultjava.model.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsersResponse();
}
