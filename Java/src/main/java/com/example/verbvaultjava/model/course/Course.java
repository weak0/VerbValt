package com.example.verbvaultjava.model.course;

import com.example.verbvaultjava.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseLevel;

    @ManyToMany(mappedBy = "courses")
    private List<User> users = new ArrayList<>();
    @OneToMany(mappedBy = "course")
    private List<CourseSentence> courseSentences = new ArrayList<>();
    @OneToMany(mappedBy = "course")
    private List<CourseWord> courseWords = new ArrayList<>();

}
