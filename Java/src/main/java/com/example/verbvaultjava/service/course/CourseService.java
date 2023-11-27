package com.example.verbvaultjava.service.course;

import com.example.verbvaultjava.model.dto.CourseDto;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.model.dto.CourseInfo;

import java.util.List;

public interface CourseService {
    Course createCourse(CourseDto courseDto);
    List<Course> readAllCourses();
    CourseInfo getCourseInfo(Long courseId);
}
