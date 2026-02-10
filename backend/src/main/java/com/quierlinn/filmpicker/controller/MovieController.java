package com.quierlinn.filmpicker.controller;

import com.quierlinn.filmpicker.dto.tmdb.TmdbSearchResult;
import com.quierlinn.filmpicker.entity.Movie;
import com.quierlinn.filmpicker.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/search")
    public ResponseEntity<List<TmdbSearchResult>> searchMovies(@RequestParam String query) {
        return ResponseEntity.ok(movieService.searchMovies(query));
    }

    @GetMapping("/tmdb/{tmdbId}")
    public ResponseEntity<Movie> getMovieByTmdbId(@PathVariable Integer tmdbId) {
        Movie movie = movieService.getOrCreateMovieByTmdbId(tmdbId);
        return ResponseEntity.ok(movie);
    }
}
