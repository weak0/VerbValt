package com.example.verbvaultjava.model.course;

import com.example.verbvaultjava.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


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
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy ="course")
    private List<CourseSentence>courseSentences;
    @OneToMany(mappedBy = "course")
    private List<CourseWord>courseWords;

}
