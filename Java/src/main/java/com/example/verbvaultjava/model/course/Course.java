package com.example.verbvaultjava.model.course;

import com.example.verbvaultjava.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Entity
@Data
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseLevel;

    @ManyToMany(mappedBy = "courses")
    private List<User> users;
    @OneToMany(mappedBy = "course")
    private Set<CourseSentence> courseSentences;
    @OneToMany(mappedBy = "course")
    private Set<CourseWord> courseWords;

}
