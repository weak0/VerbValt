package com.example.verbvaultjava.controller;

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
