package com.example.verbvaultjava.controller;

import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.dto.UserDto;
import com.example.verbvaultjava.model.dto.UserResponse;
import com.example.verbvaultjava.model.dto.WordDto;
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
    public ResponseEntity<List<User>> getUsersWithWorlds() {
        return ResponseEntity.ok(userService.getUsers());
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse>getUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUsersResponse(userId));
    }
    @GetMapping("{userId}/words")
    public ResponseEntity<List<WordDto>>getUsersWord(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUsersWord(userId));
    }
    @GetMapping("{userId}/words/random")
    public ResponseEntity<WordDto>getRandomWord(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getRandomWord(userId));
    }
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        User user = userService.createUser(userDto);
        return ResponseEntity.created(URI.create("/" + user.getId())).build();
    }
    @PostMapping("/{userId}/addWord")
    public ResponseEntity<WordDto> addWordToUser(@PathVariable Long userId, @RequestBody WordDto wordDto) {
        return ResponseEntity.ok(userService.addWordToUser(userId, wordDto));
    }
}
