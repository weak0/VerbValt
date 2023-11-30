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
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;


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
    public List<WordDto> getUsersWord(Long userId) {
        User userFromDb = getUserFromDb(userId);
        List<Word> words = userFromDb.getWords();
        validUserWords(words);
        return words.stream()
                .map(word -> WordDto.builder()
                        .foreignWord(word.getForeignWord())
                        .translation(word.getTranslation()).build()).toList();
    }

    private void validUserWords(List<Word> words) {
        if (words.isEmpty()) {
            throw new IllegalArgumentException("User have no words !");
        }
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
    public WordDto getRandomWord(Long userId) {
        User userFromDb = getUserFromDb(userId);
        validUserWords(userFromDb.getWords());
        Random random = new Random();
        int index = random.nextInt(userFromDb.getWords().size());
        Word word = userFromDb.getWords().get(index);
        return WordDto.builder()
                .translation(word.getTranslation())
                .foreignWord(word.getForeignWord())
                .build();
    }

    @Override
    public String validForeignWord(String word, String translate, Long userId) {
        User userFromDb = getUserFromDb(userId);
        Word userWord = userFromDb.getWords().stream()
                .filter(w -> w.getForeignWord().equals(word))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Given word do not exist in that course !"));
        String response;
        JSONObject jsonObject = new JSONObject(translate);
        translate = jsonObject.getString("translate");
        if (userWord.getTranslation().equals(translate)) {
            response = "Brawo, tak trzymaj";
        } else {
            response = "Niestety nie udało się, sprobuj ponownie";
        }
        return response;
    }

    @Override
    public String validTranslateWord(String word, String foreignWord, Long userId) {
        User userFromDb = getUserFromDb(userId);
        Word userWord = userFromDb.getWords().stream()
                .filter(w -> w.getTranslation().equals(word))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Given word do not exist in that course !"));
        String response;
        JSONObject jsonObject = new JSONObject(foreignWord);
        foreignWord = jsonObject.getString("foreignWord");
        if (userWord.getTranslation().equals(foreignWord)) {
            response = "Brawo, tak trzymaj";
        } else {
            response = "Niestety nie udało się, sprobuj ponownie";
        }
        return response;

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
