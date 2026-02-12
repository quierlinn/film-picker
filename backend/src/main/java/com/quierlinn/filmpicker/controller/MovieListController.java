package com.quierlinn.filmpicker.controller;

import com.quierlinn.filmpicker.dto.MovieListRequest;
import com.quierlinn.filmpicker.service.MovieListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lists")
@RequiredArgsConstructor
public class MovieListController {

    private final MovieListService listService;

    @PostMapping
    public ResponseEntity<?> createList(@Valid @RequestBody MovieListRequest request) {
        listService.createList(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getUserLists() {
        return ResponseEntity.ok(listService.getUserLists());
    }

    @PostMapping("/{listId}/movies")
    public ResponseEntity<?> addMovieToList(@PathVariable Long listId, @RequestBody AddMovieRequest body) {
        listService.addMovieToList(listId, body.getMovieId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{listId}")
    public ResponseEntity<?> getList(@PathVariable Long listId) {
        return ResponseEntity.ok(listService.getListWithMovies(listId));
    }

    public static class AddMovieRequest {
        private Long movieId;

        public Long getMovieId() { return movieId; }
        public void setMovieId(Long movieId) { this.movieId = movieId; }
    }
}
