package com.example.verbvaultjava.service.course;

import com.example.verbvaultjava.model.dto.CourseDto;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.model.dto.CourseInfo;
import com.example.verbvaultjava.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;
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
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("User id not found"));
        CourseInfo courseInfo = CourseInfo.builder()
                .courseSentences(course.getCourseSentences())
                .courseWords(course.getCourseWords())
                .build();
        return courseInfo;
    }
}
