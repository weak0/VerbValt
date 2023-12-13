package com.example.verbvaultjava.controller;

import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.dto.*;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> readAllCourses() {
        return ResponseEntity.ok(courseService.readAllCourses());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody CourseDto courseDto) {
        Course course = courseService.createCourse(courseDto);
        return ResponseEntity.created(URI.create("/" + course.getId())).build();
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseInfo> readCourseInfo(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseInfo(courseId));
    }

    @PostMapping("/{courseId}/users/{userId}")
    public ResponseEntity<User> addUserToCourse(@PathVariable Long courseId, @PathVariable Long userId) {
        User user = courseService.addUerToCourse(courseId, userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{courseId}/words")
    public ResponseEntity<List<WordDto>> readAllWordsCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.readAllWordsFromCourse(courseId));
    }

    @GetMapping("/{courseId}/words/random")
    public ResponseEntity<WordDto> readRandomWord(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.readRandomWordFromCourse(courseId));
    }

    @PostMapping("/{courseId}/words/translate")
    public ResponseEntity<CourseWordResponseDto> validForeignWord(@RequestParam(name = "word") String word, @RequestBody CourseWordRequestDto courseWordDto, @PathVariable Long courseId) {
        CourseWordResponseDto response = courseService.validForeignWord(word, courseWordDto, courseId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{courseId}/words/foreign")
    public ResponseEntity<CourseWordResponseDto> validTranslateWord(@RequestParam(name = "word") String word, @RequestBody CourseWordRequestDto courseWordDto, @PathVariable Long courseId) {
        CourseWordResponseDto response = courseService.validTranslateWord(word, courseWordDto, courseId);
        return ResponseEntity.ok(response);
    }
}
