package com.example.verbvaultjava.model.course;

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
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @OneToOne
    @JoinColumn(name = "course_word_id")
    private CourseWord courseWord;

}
