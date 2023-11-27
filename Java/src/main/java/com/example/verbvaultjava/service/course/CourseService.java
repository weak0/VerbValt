package com.example.verbvaultjava.service.course;

import com.example.verbvaultjava.model.CourseDto;
import com.example.verbvaultjava.model.course.Course;

import java.util.List;

public interface CourseService {
    Course createCourse(CourseDto courseDto);
    List<Course> readAllCourses();
}
