package com.example.verbvaultjava.service.user;


import com.example.verbvaultjava.model.Role;
import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.Word;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.model.course.UserCourse;
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

    @Override
    public UserResponse getUsersResponse(Long userId) {
        User userFromDb = getUserFromDb(userId);
        UserResponse userResponse = UserResponse.builder()
                .username(userFromDb.getUsername())
                .email(userFromDb.getEmail())
                .roleName(userFromDb.getRole().getRoleName())
                .build();
        List<UserCourse> userCourses = userCourseRepository.findByUserId(userId);
        if (userCourses.isEmpty()) {
            return userResponse;
        } else {
            List<Course> courses = userFromDb.getCourses();
            userResponse.setCourses(courses.stream()
                    .map(course -> {
                        Optional<UserCourse> userCourseOptional = userCourses.stream()
                                .filter(uc -> uc.getCourse().equals(course))
                                .findFirst();

                        return course.getCourseLevel() +
                                " progress = " +
                                userCourseOptional.map(UserCourse::getProgress).orElse(0); // domyślnie 0, można dostosować
                    })
                    .toList());
        }


        return userResponse;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
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
        User userFromDb = getUserFromDb(userId);
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

    private User getUserFromDb(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with given id do not exists !"));
    }
}
