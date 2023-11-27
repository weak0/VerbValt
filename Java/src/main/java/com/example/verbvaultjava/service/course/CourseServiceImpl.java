package com.example.verbvaultjava.service.course;

import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.course.CourseWord;
import com.example.verbvaultjava.model.course.UserCourse;
import com.example.verbvaultjava.model.dto.CourseDto;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.model.dto.CourseInfo;
import com.example.verbvaultjava.model.dto.WordDto;
import com.example.verbvaultjava.repository.CourseRepository;
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
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;
    @Override
    public Course createCourse(CourseDto courseDto) {
        Optional<Course> byCourseLevelIgnoreCase = courseRepository.findByCourseLevelIgnoreCase(courseDto.getCourseLevel());
        Course course;
        if (byCourseLevelIgnoreCase.isEmpty()){
            course = new Course();
            course.setCourseLevel(courseDto.getCourseLevel());
        }
        else {
            course= byCourseLevelIgnoreCase.get();
        }
        return courseRepository.save(course);
    }
    @Override
    public List<Course> readAllCourses(){
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
        if (courseWords.isEmpty()){
            throw new IllegalArgumentException("Course with given id do not have any word !");
        }
        Random rnd = new Random();
        int randomIndex = rnd.nextInt(courseWords.size());
        return WordDto.builder()
                .foreignWord(courseWords.get(randomIndex).getForeignWord())
                .translation(courseWords.get(randomIndex).getTranslation())
                .build();
    }
}
