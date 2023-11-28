package com.example.verbvaultjava.service.user;


import com.example.verbvaultjava.model.Role;
import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.Word;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.model.dto.UserDto;
import com.example.verbvaultjava.model.dto.UserResponse;
import com.example.verbvaultjava.model.dto.WordDto;
import com.example.verbvaultjava.repository.RoleRepository;
import com.example.verbvaultjava.repository.UserCourseRepository;
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
    private final UserCourseRepository userCourseRepository;

    public List<UserResponse> getUsersResponse() {
        List<User> all = userRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();
        int progress;
        for (User user : all) {
            if (userCourseRepository.findUserCourseByUserId(user.getId()).isPresent()) {
                progress = userCourseRepository.findUserCourseByUserId(user.getId()).get().getProgress();
            } else {
                progress = 0;
            }
            UserResponse userResponse = UserResponse.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .id(user.getId())
                    .progress(progress)
                    .courses(user.getCourses().stream()
                            .map(Course::getCourseLevel).collect(Collectors.toList()))
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
        } else {
            role = byRoleName.get();
        }
        User user = User.builder().username(userDto.getUserName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .role(role).build();

        roleRepository.save(role);
        return userRepository.save(user);
    }

    @Override
    public WordDto addWordToUser(Long userId, WordDto wordDto) {
        User userFromDb = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with given id do not exists !"));
        List<Word> words = userFromDb.getWords();
        boolean isExistsAlready = words.stream()
                .anyMatch(w -> w.getForeignWord().equals(wordDto.getForeignWord()));
        if (!isExistsAlready) {
            words.add(Word.builder()
                    .foreignWord(wordDto.getForeignWord())
                    .translation(wordDto.getTranslation())
                    .user(userFromDb)
                    .build());
        } else {
            throw new IllegalArgumentException("Given word already exists !");
        }
        userRepository.save(userFromDb);
        return wordDto;
    }
}
