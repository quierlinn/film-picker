package com.quierlinn.filmpicker.repository;

import com.quierlinn.filmpicker.entity.MovieList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieListRepository extends JpaRepository<MovieList, Long> {
    List<MovieList> findByUserId(Long userId);
}
