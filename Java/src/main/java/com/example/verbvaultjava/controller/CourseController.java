package com.example.verbvaultjava.controller;

import com.example.verbvaultjava.model.dto.CourseDto;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.model.dto.CourseInfo;
import com.example.verbvaultjava.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    @GetMapping
    public ResponseEntity<List<Course>>readAllCourses(){
        return ResponseEntity.ok(courseService.readAllCourses());
    }
    @PostMapping
    public ResponseEntity<Course>createCourse(@RequestBody CourseDto courseDto){
        Course course = courseService.createCourse(courseDto);
        return ResponseEntity.created(URI.create("/"+course.getId())).build();
    }
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseInfo>readCourseInfo(@PathVariable Long courseId){
        return ResponseEntity.ok(courseService.getCourseInfo(courseId));
    }
}
