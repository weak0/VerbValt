package com.example.verbvaultjava.repository;

import com.example.verbvaultjava.model.CourseWorld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseWorldRepository extends JpaRepository<CourseWorld,Long> {
}
