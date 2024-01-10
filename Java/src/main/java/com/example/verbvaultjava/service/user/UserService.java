package com.example.verbvaultjava.service.user;

import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.dto.*;

import java.util.List;

public interface UserService {
    UserResponse getUsersResponse(Long userId);
    InitWord addWordToUser(Long userId,InitWord initWord);
    List<User> getUsers();

    List<WordDto> getUsersWord(Long userId);

    WordDto getRandomWord(Long userId);

    WordResponseDto validForeignWord(WordRequestDto wordRequestDto);

    WordResponseDto validTranslateWord(WordRequestDto wordRequestDto);
    void deleteUserWord(Long userId,Long wordId);
    InitWord updateWord(Long userId, WordDto wordDto);
    void deleteUser(Long userId);
}
