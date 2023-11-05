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
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

   @OneToMany(mappedBy = "user")
    private List<Word> words;
   @ManyToMany
   @JoinTable(
           name = "user_course",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "course_id")
   )
    private List<Course>courses;

}
