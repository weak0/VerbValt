package com.example.verbvaultjava.repository;

import com.example.verbvaultjava.model.course.CourseWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseWordRepository extends JpaRepository<CourseWord,Long> {
    boolean existsByForeignWord(String foreignWord);
}
