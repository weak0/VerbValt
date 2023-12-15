package com.example.verbvaultjava.controller;

import com.example.verbvaultjava.auth.AuthenticationRequest;
import com.example.verbvaultjava.auth.AuthenticationResponse;
import com.example.verbvaultjava.auth.AuthenticationService;
import com.example.verbvaultjava.auth.RegisterRequest;
import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.dto.*;
import com.example.verbvaultjava.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationService authService;

    @GetMapping
    public ResponseEntity<List<User>> getUsersWithWorlds() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUsersResponse(userId));
    }

    @GetMapping("{userId}/words")
    public ResponseEntity<List<WordDto>> getUsersWord(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUsersWord(userId));
    }

    @GetMapping("{userId}/words/random")
    public ResponseEntity<WordDto> getRandomWord(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getRandomWord(userId));
    }

    @PostMapping("/words/translate")
    public ResponseEntity<WordResponseDto> validForeignWord(@RequestBody WordRequestDto courseWordDto) {
        WordResponseDto response = userService.validForeignWord(courseWordDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/words/foreign")
    public ResponseEntity<WordResponseDto> validTranslateWord(@RequestBody WordRequestDto courseWordDto) {
        WordResponseDto response = userService.validTranslateWord(courseWordDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> createUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/{userId}/words")
    public ResponseEntity<InitWord> addWordToUser(@PathVariable Long userId, @RequestBody InitWord initWord) {
        return ResponseEntity.ok(userService.addWordToUser(userId, initWord));
    }
}
