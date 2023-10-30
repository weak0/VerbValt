package com.example.verbvaultjava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "worlds")
public class World {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foreignWord;
    private String translation;
}
