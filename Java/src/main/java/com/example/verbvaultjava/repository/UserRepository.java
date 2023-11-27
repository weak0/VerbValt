package com.example.verbvaultjava.repository;

import com.example.verbvaultjava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {
  List<User>findAll();

}
