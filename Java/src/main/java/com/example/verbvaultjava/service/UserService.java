package com.example.verbvaultjava.service;


import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.World;
import com.example.verbvaultjava.model.dto.UserResponse;
import com.example.verbvaultjava.model.dto.WorldDto;
import com.example.verbvaultjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> getUsersResponse() {
        List<User> all = userRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();

        for (User user : all) {
            UserResponse userResponse = UserResponse.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .worlds(user.getWorlds().stream()
                            .map(world -> {
                                WorldDto worldDto = WorldDto.builder()
                                        .foreignWord(world.getForeignWord())
                                        .translation(world.getTranslation())
                                        .build();
                                return worldDto;
                            }).collect(Collectors.toSet()))
                    .build();
            responses.add(userResponse);
        }
        return responses;
    }


}
