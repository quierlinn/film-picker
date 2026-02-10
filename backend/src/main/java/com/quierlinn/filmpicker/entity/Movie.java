package com.quierlinn.filmpicker.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "movies", uniqueConstraints = @UniqueConstraint(columnNames = "tmdb_id"))
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tmdb_id", unique = true, nullable = false)
    private Integer tmdbId;

    @Column(nullable = false, length = 500)
    private String title;

    private Integer releaseYear;

    @Column(length = 200)
    private String genre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 500)
    private String posterPath;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
