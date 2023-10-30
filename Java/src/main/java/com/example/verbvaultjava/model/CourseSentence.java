package com.example.verbvaultjava.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "course_sentences")
public class CourseSentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foreignSentence;
    @OneToOne
    private CourseWorld courseWorld;
}
