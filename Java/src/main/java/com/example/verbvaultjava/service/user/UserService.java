package com.example.verbvaultjava.service.user;

import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.dto.UserDto;
import com.example.verbvaultjava.model.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsersResponse();
    User createUser(UserDto userDto);
}
