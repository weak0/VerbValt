package com.example.verbvaultjava.service.course;

import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.model.course.CourseWord;
import com.example.verbvaultjava.model.course.UserCourse;
import com.example.verbvaultjava.model.dto.*;
import com.example.verbvaultjava.repository.CourseRepository;
import com.example.verbvaultjava.repository.CourseWordRepository;
import com.example.verbvaultjava.repository.UserCourseRepository;
import com.example.verbvaultjava.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;
    private final CourseWordRepository courseWordRepository;

    @Override
    public Course createCourse(CourseDto courseDto) {
        Optional<Course> byCourseLevelIgnoreCase = courseRepository.findByCourseLevelIgnoreCase(courseDto.getCourseLevel());
        Course course;
        if (byCourseLevelIgnoreCase.isEmpty()) {
            course = new Course();
            course.setCourseLevel(courseDto.getCourseLevel());
        } else {
            course = byCourseLevelIgnoreCase.get();
        }
        return courseRepository.save(course);
    }

    @Override
    public List<Course> readAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public CourseInfo getCourseInfo(Long courseId) {
        Course course = getCourseFromDb(courseId);
        CourseInfo courseInfo = CourseInfo.builder()
                .courseSentences(course.getCourseSentences())
                .courseWords(course.getCourseWords())
                .build();
        return courseInfo;
    }

    private Course getCourseFromDb(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course id not found"));
    }

    @Transactional
    @Override
    public User addUerToCourse(Long courseId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User id not found !"));
        Course courseFromDb = getCourseFromDb(courseId);
        validUserCourse(userId, courseFromDb);
        UserCourse userCourse = new UserCourse();
        userCourse.setUser(user);
        userCourse.setCourse(courseFromDb);
        userCourse.setProgress(0);
        userCourseRepository.save(userCourse);
        return user;
    }

    @Override
    public List<WordDto> readAllWordsFromCourse(Long courseId) {
        Course courseFromDb = getCourseFromDb(courseId);
        return courseFromDb.getCourseWords().stream()
                .map(w -> {
                    WordDto wordDto = WordDto.builder()
                            .wordId(w.getId())
                            .foreignWord(w.getForeignWord())
                            .translation(w.getTranslation())
                            .build();
                    return wordDto;
                }).collect(Collectors.toList());

    }

    @Override
    public WordDto readRandomWordFromCourse(Long courseId) {
        Course courseFromDb = getCourseFromDb(courseId);
        List<CourseWord> courseWords = courseFromDb.getCourseWords();
        if (courseWords.isEmpty()) {
            throw new IllegalArgumentException("Course with given id do not have any word !");
        }
        Random rnd = new Random();
        int randomIndex = rnd.nextInt(courseWords.size());
        return WordDto.builder()
                .wordId(courseWords.get(randomIndex).getId())
                .foreignWord(courseWords.get(randomIndex).getForeignWord())
                .translation(courseWords.get(randomIndex).getTranslation())
                .build();
    }

    @Override
    public CourseWordResponseDto validForeignWord(CourseWordRequestDto courseWordDto, Long courseId) {
        String foreignWord = getCourseWord(courseWordDto).getForeignWord();
        Long userId = courseWordDto.getUserId();
        Course courseFromDb = getCourseFromDb(courseId);
        String word = courseWordDto.getWord();
        CourseWord courseWord = courseFromDb.getCourseWords()
                .stream()
                .filter(w -> w.getForeignWord().equals(foreignWord))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Given word do not exist in that course !"));
        String response;
        UserCourse userCourse = getUserCourse(userId);
        if (courseWord.getTranslation().equals(word)) {
            int progress = userCourse.getProgress();
            progress++;
            userCourse.setProgress(progress);
            response = "Brawo, tak trzymaj";
        } else {
            response = "Niestety nie udało się, sprobuj ponownie";
        }
        userCourseRepository.save(userCourse);
        return CourseWordResponseDto.builder()
                .wordId(courseWord.getId())
                .status(response)
                .build();
    }

    @Override
    public CourseWordResponseDto validTranslateWord(CourseWordRequestDto courseWordDto, Long courseId) {
        Course courseFromDb = getCourseFromDb(courseId);
        Long userId = courseWordDto.getUserId();
        String foreignWord = courseWordDto.getWord();
        CourseWord word = getCourseWord(courseWordDto);
        String translation = word.getTranslation();
        CourseWord courseWord = courseFromDb.getCourseWords()
                .stream()
                .filter(w -> w.getTranslation().equals(translation))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Given word do not exists in that course"));
        String response;
        UserCourse userCourse = getUserCourse(userId);
        if (courseWord.getForeignWord().equals(foreignWord)) {
            int progress = userCourse.getProgress();
            if (progress < 80) {
                progress++;
            }
            userCourse.setProgress(progress);
            response = "Brawo, tak trzymaj";
        } else {
            response = "Niestety nie udało się, spróbuj ponownie !";
        }
        userCourseRepository.save(userCourse);
        return CourseWordResponseDto.builder()
                .wordId(courseWord.getId())
                .status(response)
                .build();
    }

    private void validUserCourse(Long userId, Course course) {
        boolean isInCourse = course.getUsers().stream()
                .anyMatch(u -> u.getId().equals(userId));
        if (isInCourse) {
            throw new IllegalArgumentException("User with given id is already in this course !");
        }
    }

    private CourseWord getCourseWord(CourseWordRequestDto courseWordDto) {
        CourseWord word = courseWordRepository.findById(courseWordDto.getWordId())
                .orElseThrow(() -> new IllegalArgumentException("Word with given id do not exists !"));
        return word;
    }

    private UserCourse getUserCourse(Long userId) {
        return userCourseRepository.findUserCourseByUserId(userId).orElseThrow(() -> new IllegalArgumentException("User with given id not found !"));
    }
}
