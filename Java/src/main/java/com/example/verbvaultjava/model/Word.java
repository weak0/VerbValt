package com.example.verbvaultjava.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foreignWord;
    private String translation;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
