package com.example.verbvaultjava.service.user;


import com.example.verbvaultjava.exception.UserNotFoundException;
import com.example.verbvaultjava.exception.UserWordAlreadyExistsException;
import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.Word;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.model.course.UserCourse;
import com.example.verbvaultjava.model.dto.*;
import com.example.verbvaultjava.repository.UserCourseRepository;
import com.example.verbvaultjava.repository.UserRepository;
import com.example.verbvaultjava.repository.WordRepository;
import com.example.verbvaultjava.token.Token;
import com.example.verbvaultjava.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;
    private final WordRepository wordRepository;
    private final TokenRepository tokenRepository;

    @Override
    public UserResponse getUsersResponse(Long userId) {
        User userFromDb = getUserFromDb(userId);
        UserResponse userResponse = UserResponse.builder()
                .username(userFromDb.getUsername())
                .email(userFromDb.getEmail())
                .roleName(userFromDb.getRole().name())
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
            throw new UserNotFoundException("User have no words !");
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
        Word word = getWord(wordRequestDto.getWordId());
        String translate = wordRequestDto.getWord().toLowerCase();
        Word userWord = userFromDb.getWords().stream()
                .filter(w -> w.getForeignWord().equals(word.getForeignWord()))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Given word do not exist in that course !"));
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
        Word word = getWord(wordRequestDto.getWordId());
        String foreign = wordRequestDto.getWord().toLowerCase();
        Word userWord = userFromDb.getWords().stream()
                .filter(w -> w.getTranslation().equals(word.getTranslation()))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Given word do not exist in that course !"));
        String response;
        if (userWord.getForeignWord().equals(foreign)) {
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
            throw new UserWordAlreadyExistsException("Given word already exists !");
        }
        userRepository.save(userFromDb);
        return initWord;
    }

    @Override
    public InitWord updateWord(Long userId, WordDto wordDto) {
        User userFromDb = getUserFromDb(userId);
        Word word = getWord(wordDto.getWordId());
        word.setForeignWord(wordDto.getForeignWord());
        word.setTranslation(wordDto.getTranslation());
        int index = userFromDb.getWords().indexOf(word);
        userFromDb.getWords().set(index, word);
        userRepository.save(userFromDb);
        return InitWord.builder()
                .foreign(word.getForeignWord())
                .translate(word.getTranslation())
                .build();
    }

    @Override
    public void deleteUserWord(Long userId, Long wordId) {
        Word word = getWord(wordId);
        User userFromDb = getUserFromDb(userId);
        userFromDb.getWords().remove(word);
        userRepository.save(userFromDb);
    }

    @Override
    public void deleteUser(Long userId) {
        User userFromDb = getUserFromDb(userId);
        userRepository.delete(userFromDb);
    }

    private User getUserFromDb(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with given id do not exists !"));
    }

    private Word getWord(Long wordId) {
        return wordRepository.findById(wordId)
                .orElseThrow(() -> new UserNotFoundException("Word with given id do not exists !"));
    }
}
