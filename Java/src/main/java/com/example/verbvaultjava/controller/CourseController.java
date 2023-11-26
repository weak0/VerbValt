package com.example.verbvaultjava.controller;

import com.example.verbvaultjava.model.CourseDto;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    @PostMapping
    public ResponseEntity<Course>createCourse(@RequestBody CourseDto courseDto){
        Course course = courseService.createCourse(courseDto);
        return ResponseEntity.created(URI.create("/"+course.getId())).build();
    }
}
