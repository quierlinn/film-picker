package com.quierlinn.filmpicker.repository;

import com.quierlinn.filmpicker.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserIdAndMovieId(Long userId, Long movieId);

    @Query("SELECT r FROM Rating r WHERE r.movie.id = :movieId")
    List<Rating> findAllByMovieId(@Param("movieId") Long movieId);

    @Query("""
        SELECT m, AVG(r.score) as avgScore
        FROM Movie m
        JOIN Rating r ON m.id = r.movie.id
        GROUP BY m.id
        ORDER BY avgScore DESC
        """)
    List<Object[]> findTopRatedMovies();
}
