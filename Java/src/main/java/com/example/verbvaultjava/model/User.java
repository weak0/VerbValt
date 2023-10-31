package com.example.verbvaultjava.model;

import com.example.verbvaultjava.model.course.Course;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
   @OneToMany(mappedBy = "user")
    private List<Word> words;
   @OneToOne
   @JoinColumn(name = "course_id")
    private Course course;


}
