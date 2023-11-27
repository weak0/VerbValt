package com.example.verbvaultjava.repository;

import com.example.verbvaultjava.model.course.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse,Long> {
    Optional<UserCourse>findUserCourseByUserId(Long userId);
}
