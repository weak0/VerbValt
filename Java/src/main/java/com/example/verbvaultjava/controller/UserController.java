package com.example.verbvaultjava.controller;

import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.dto.UserDto;
import com.example.verbvaultjava.model.dto.UserResponse;
import com.example.verbvaultjava.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    @PostMapping
    public ResponseEntity<String>createUser(@RequestBody UserDto userDto){
        User user = userService.createUser(userDto);
        return ResponseEntity.created(URI.create("/"+user.getId())).build();
    }
}
