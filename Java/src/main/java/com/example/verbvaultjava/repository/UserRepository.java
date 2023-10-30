package com.example.verbvaultjava.repository;

import com.example.verbvaultjava.model.User;
import com.example.verbvaultjava.model.dto.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {
  List<User>findAll();

}
