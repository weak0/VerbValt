package com.example.verbvaultjava.repository;

import com.example.verbvaultjava.model.course.CourseSentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSentenceRepository extends JpaRepository<CourseSentence,Long> {
    boolean existsByForeignSentence(String foreignSentence);
}
