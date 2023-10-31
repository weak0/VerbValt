package com.example.verbvaultjava.controller;

import com.example.verbvaultjava.model.dto.UserResponse;
import com.example.verbvaultjava.service.UserService;
import com.example.verbvaultjava.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
private final UserService userService;
    @GetMapping
    public ResponseEntity<List<UserResponse>>getUsersWithWorlds(){

        return ResponseEntity.ok(userService.getUsersResponse());
    }
}
