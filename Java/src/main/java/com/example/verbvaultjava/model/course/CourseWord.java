package com.example.verbvaultjava.model.course;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "course_words")
public class CourseWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foreignWord;
    private String translation;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
