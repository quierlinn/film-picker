package com.quierlinn.filmpicker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "movie_list_items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"list_id", "movie_id"}))
@Data
public class MovieListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id", nullable = false)
    private MovieList list;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
}
