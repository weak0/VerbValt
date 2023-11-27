package com.example.verbvaultjava.service.course;

import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.dto.CourseDto;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.model.dto.CourseInfo;
import com.example.verbvaultjava.model.dto.WordDto;

import java.util.List;

public interface CourseService {
    Course createCourse(CourseDto courseDto);
    List<Course> readAllCourses();
    CourseInfo getCourseInfo(Long courseId);
    User addUerToCourse(Long courseId,Long userId);
    List<WordDto> readAllWordsFromCourse(Long courseId);
    WordDto readRandomWordFromCourse(Long courseId);
}
