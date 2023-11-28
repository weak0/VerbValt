package com.example.verbvaultjava.service.user;

import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.dto.UserDto;
import com.example.verbvaultjava.model.dto.UserResponse;
import com.example.verbvaultjava.model.dto.WordDto;

import java.util.List;

public interface UserService {
    UserResponse getUsersResponse(Long userId);
    User createUser(UserDto userDto);
    WordDto addWordToUser(Long userId,WordDto wordDto);
    List<User> getUsers();

    List<WordDto> getUsersWord(Long userId);

    WordDto getRandomWord(Long userId);

}
