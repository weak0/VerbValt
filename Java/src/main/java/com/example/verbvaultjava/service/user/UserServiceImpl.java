package com.example.verbvaultjava.service.user;


import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.Word;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.model.course.UserCourse;
import com.example.verbvaultjava.model.dto.*;
import com.example.verbvaultjava.repository.RoleRepository;
import com.example.verbvaultjava.repository.UserCourseRepository;
import com.example.verbvaultjava.repository.UserRepository;
import com.example.verbvaultjava.repository.WordRepository;
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
    private final WordRepository wordRepository;

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
                        .wordId(word.getId())
                        .foreignWord(word.getForeignWord())
                        .translation(word.getTranslation()).build()).toList();
    }

    private void validUserWords(List<Word> words) {
        if (words.isEmpty()) {
            throw new IllegalArgumentException("User have no words !");
        }
    }

    @Override
    public WordDto getRandomWord(Long userId) {
        User userFromDb = getUserFromDb(userId);
        validUserWords(userFromDb.getWords());
        Random random = new Random();
        int index = random.nextInt(userFromDb.getWords().size());
        Word word = userFromDb.getWords().get(index);
        return WordDto.builder()
                .wordId(word.getId())
                .translation(word.getTranslation())
                .foreignWord(word.getForeignWord())
                .build();
    }

    @Override
    public WordResponseDto validForeignWord(WordRequestDto wordRequestDto) {
        User userFromDb = getUserFromDb(wordRequestDto.getUserId());
        Word word = getWord(wordRequestDto);
        String translate = wordRequestDto.getWord().toLowerCase();
        Word userWord = userFromDb.getWords().stream()
                .filter(w -> w.getForeignWord().equals(word.getForeignWord()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Given word do not exist in that course !"));
        String response;
        if (userWord.getTranslation().equals(translate)) {
            response = "Brawo, tak trzymaj";
        } else {
            response = "Niestety nie udało się, sprobuj ponownie";
        }
        return WordResponseDto.builder()
                .wordId(userWord.getId())
                .status(response)
                .build();
    }

    @Override
    public WordResponseDto validTranslateWord(WordRequestDto wordRequestDto) {
        User userFromDb = getUserFromDb(wordRequestDto.getUserId());
        Word word = getWord(wordRequestDto);
        String foreign = wordRequestDto.getWord().toLowerCase();
        Word userWord = userFromDb.getWords().stream()
                .filter(w -> w.getTranslation().equals(word.getTranslation()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Given word do not exist in that course !"));
        String response;
        if (userWord.getTranslation().equals(foreign)) {
            response = "Brawo, tak trzymaj";
        } else {
            response = "Niestety nie udało się, sprobuj ponownie";
        }
        return WordResponseDto.builder()
                .wordId(userWord.getId())
                .status(response)
                .build();

    }

    @Override
    public InitWord addWordToUser(Long userId, InitWord initWord) {
        User userFromDb = getUserFromDb(userId);
        List<Word> words = userFromDb.getWords();
        boolean isExistsAlready = words.stream()
                .anyMatch(w -> w.getForeignWord().equals(initWord.getForeign()));
        if (!isExistsAlready) {
            words.add(Word.builder()
                    .foreignWord(initWord.getForeign().toLowerCase())
                    .translation(initWord.getTranslate().toLowerCase())
                    .user(userFromDb)
                    .build());
        } else {
            throw new IllegalArgumentException("Given word already exists !");
        }
        userRepository.save(userFromDb);
        return initWord;
    }

    private User getUserFromDb(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with given id do not exists !"));
    }

    private Word getWord(WordRequestDto wordRequestDto) {
        return wordRepository.findById(wordRequestDto.getWordId())
                .orElseThrow(() -> new IllegalArgumentException("Word with given id do not exists !"));
    }
}
