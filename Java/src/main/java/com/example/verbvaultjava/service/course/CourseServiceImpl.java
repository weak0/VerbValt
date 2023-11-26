package com.example.verbvaultjava.service.course;

import com.example.verbvaultjava.model.CourseDto;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
