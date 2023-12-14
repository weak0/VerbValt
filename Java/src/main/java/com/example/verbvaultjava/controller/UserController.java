package com.example.verbvaultjava.controller;

import com.example.verbvaultjava.auth.AuthenticationRequest;
import com.example.verbvaultjava.auth.AuthenticationResponse;
import com.example.verbvaultjava.auth.AuthenticationService;
import com.example.verbvaultjava.auth.RegisterRequest;
import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.dto.UserDto;
import com.example.verbvaultjava.model.dto.UserResponse;
import com.example.verbvaultjava.model.dto.WordDto;
import com.example.verbvaultjava.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/{userId}/words/translate")
    public ResponseEntity<Map<String, Object>> validForeignWord(@RequestParam(name = "word") String word, @RequestBody String translate, @PathVariable Long userId) {
        String status = userService.validForeignWord(word, translate, userId);
        return ResponseEntity.ok(new JSONObject().put("status", status).toMap());
    }

    @PostMapping("/{userId}/words/foreign")
    public ResponseEntity<Map<String, Object>> validTranslateWord(@RequestParam(name = "word") String word, @RequestBody String foreignWord, @PathVariable Long userId) {
        String status = userService.validTranslateWord(word, foreignWord, userId);
        return ResponseEntity.ok(new JSONObject().put("status", status).toMap());
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
    public ResponseEntity<WordDto> addWordToUser(@PathVariable Long userId, @RequestBody WordDto wordDto) {
        return ResponseEntity.ok(userService.addWordToUser(userId, wordDto));
    }
}
