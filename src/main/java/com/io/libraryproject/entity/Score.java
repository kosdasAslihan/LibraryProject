package com.io.libraryproject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "score")
public class Score {
    @Id
    private Long id;

    private int scoreValue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
