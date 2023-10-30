package com.example.verbvaultjava.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "course_worlds")
public class CourseWorld {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foreignWord;
    private String translation;

}
