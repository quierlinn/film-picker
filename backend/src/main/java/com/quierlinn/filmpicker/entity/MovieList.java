package com.quierlinn.filmpicker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "movie_lists")
@Data
public class MovieList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
