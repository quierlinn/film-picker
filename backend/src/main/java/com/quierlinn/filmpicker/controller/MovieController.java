package com.quierlinn.filmpicker.controller;

import com.quierlinn.filmpicker.dto.MovieWithRating;
import com.quierlinn.filmpicker.dto.RatingRequest;
import com.quierlinn.filmpicker.dto.tmdb.TmdbSearchResult;
import com.quierlinn.filmpicker.entity.Movie;
import com.quierlinn.filmpicker.entity.User;
import com.quierlinn.filmpicker.service.MovieService;
import com.quierlinn.filmpicker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<TmdbSearchResult>> searchMovies(@RequestParam String query) {
        return ResponseEntity.ok(movieService.searchMovies(query));
    }

    @GetMapping("/tmdb/{tmdbId}")
    public ResponseEntity<Movie> getMovieByTmdbId(@PathVariable Integer tmdbId) {
        Movie movie = movieService.getOrCreateMovieByTmdbId(tmdbId);
        return ResponseEntity.ok(movie);
    }

    @PostMapping("/movies/{movieId}/rate")
    public ResponseEntity<Void> rateMovie(@PathVariable Long movieId, @Valid @RequestBody RatingRequest request) {
        User user = userService.getCurrentUser();
        movieService.rateMovie(movieId, user.getId(), request.getScore());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/movies/top")
    public ResponseEntity<List<MovieWithRating>> getTopRatedMovies() {
        return ResponseEntity.ok(movieService.getTopRatedMovies(10));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }
}
