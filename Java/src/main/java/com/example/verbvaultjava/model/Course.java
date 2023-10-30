package com.example.verbvaultjava.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;
    private Boolean status;
    @OneToMany
    private Set<CourseSentence> courseSentenceSet=new HashSet<>();
    @OneToMany
    private Set<CourseWorld>courseWorlds=new HashSet<>();
}
