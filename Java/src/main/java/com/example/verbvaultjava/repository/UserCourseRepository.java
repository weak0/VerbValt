package com.example.verbvaultjava.repository;

import com.example.verbvaultjava.model.course.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse,Long> {
}
