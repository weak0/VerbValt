package com.example.verbvaultjava.repository;

import com.example.verbvaultjava.model.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldRepository extends JpaRepository<World,Long> {
}
