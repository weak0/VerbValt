package com.example.verbvaultjava.service.user;


import com.example.verbvaultjava.model.Role;
import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.dto.UserDto;
import com.example.verbvaultjava.model.dto.UserResponse;
import com.example.verbvaultjava.model.dto.WordDto;
import com.example.verbvaultjava.repository.RoleRepository;
import com.example.verbvaultjava.repository.UserRepository;
import com.example.verbvaultjava.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<UserResponse> getUsersResponse() {
        List<User> all = userRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();

        for (User user : all) {
            UserResponse userResponse = UserResponse.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .wordDto(user.getWords().stream()
                            .map(world -> {
                                WordDto worldDto = WordDto.builder()
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

    @Override
    public User createUser(UserDto userDto) {
        Role role;
        Optional<Role> byRoleName = roleRepository.findByRoleName(userDto.getRoleName());
        if (byRoleName.isEmpty()) {
             role = new Role();
        role.setRoleName(userDto.getRoleName());
        }else {
             role = byRoleName.get();
        }

        User user = User.builder().username(userDto.getUserName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .role(role).build();

        roleRepository.save(role);
         return userRepository.save(user);

    }
}
