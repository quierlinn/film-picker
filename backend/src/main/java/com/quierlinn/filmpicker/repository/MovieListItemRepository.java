package com.quierlinn.filmpicker.repository;

import com.quierlinn.filmpicker.entity.MovieListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieListItemRepository extends JpaRepository<MovieListItem, Long> {
    boolean existsByListIdAndMovieId(Long listId, Long movieId);
    List<MovieListItem> findByListId(Long listId);
}
