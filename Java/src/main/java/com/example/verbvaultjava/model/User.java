package com.example.verbvaultjava.model;

import com.example.verbvaultjava.model.course.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private List<Word> words= new ArrayList<>();
   @ManyToMany
   @JoinTable(
           name = "user_course",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "course_id")
   )
    private List<Course>courses= new ArrayList<>();

}
