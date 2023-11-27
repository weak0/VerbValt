package com.example.verbvaultjava.repository;

import com.example.verbvaultjava.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    Optional<Course> findByCourseLevelIgnoreCase(String courseLevel);
}
