package com.example.verbvaultjava.controller;

import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.dto.CourseDto;
import com.example.verbvaultjava.model.course.Course;
import com.example.verbvaultjava.model.dto.CourseInfo;
import com.example.verbvaultjava.model.dto.WordDto;
import com.example.verbvaultjava.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
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
    @PostMapping("/{courseId}/users/{userId}")
    public ResponseEntity<User> addUserToCourse(@PathVariable Long courseId,@PathVariable Long userId){
        User user = courseService.addUerToCourse(courseId, userId);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/{courseId}/words")
    public ResponseEntity<List<WordDto>>readAllWordsCourse(@PathVariable Long courseId){
        return ResponseEntity.ok(courseService.readAllWordsFromCourse(courseId));
    }
    @GetMapping("/{courseId}/words/random")
    public ResponseEntity<WordDto>readRandomWord(@PathVariable Long courseId){
        return ResponseEntity.ok(courseService.readRandomWordFromCourse(courseId));
    }
    @PostMapping("/{courseId}/words/users/{userId}")
    public ResponseEntity<?>validForeignWord(@RequestParam(name = "word")String word,@RequestBody String translate,@PathVariable Long courseId,@PathVariable Long userId){
        String status=courseService.validForeignWord(word,translate,courseId,userId);
        return ResponseEntity.ok(new JSONObject().put("status",status).toMap());
    }
}
