package com.example.verbvaultjava.service.course;

import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.dto.*;
import com.example.verbvaultjava.model.course.Course;

import java.util.List;

public interface CourseService {
    Course createCourse(CourseDto courseDto);
    List<Course> readAllCourses();
    CourseInfo getCourseInfo(Long courseId);
    User addUerToCourse(Long courseId,Long userId);
    List<WordDto> readAllWordsFromCourse(Long courseId);
    WordDto readRandomWordFromCourse(Long courseId);

    CourseWordResponseDto validForeignWord(CourseWordRequestDto courseWordDto, Long courseId);
    CourseWordResponseDto validTranslateWord(CourseWordRequestDto courseWordRequestDto, Long courseId);
}
