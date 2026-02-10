package com.quierlinn.filmpicker.service;

import com.quierlinn.filmpicker.client.TmdbClient;
import com.quierlinn.filmpicker.dto.tmdb.Genre;
import com.quierlinn.filmpicker.dto.tmdb.TmdbMovieDetails;
import com.quierlinn.filmpicker.dto.tmdb.TmdbSearchResult;
import com.quierlinn.filmpicker.entity.Movie;
import com.quierlinn.filmpicker.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final TmdbClient tmdbClient;
    private final MovieRepository movieRepository;

    public List<TmdbSearchResult> searchMovies(String query) {
        return tmdbClient.searchMovies(query);
    }

    public Movie getOrCreateMovieByTmdbId(Integer tmdbId) {
        return movieRepository.findByTmdbId(tmdbId)
                .orElseGet(() -> fetchAndSaveMovie(tmdbId));
    }

    private Movie fetchAndSaveMovie(Integer tmdbId) {
        var details = tmdbClient.getMovieDetails(tmdbId);

        Movie movie = new Movie();
        movie.setTmdbId(details.getId());
        movie.setTitle(details.getTitle());
        movie.setDescription(details.getOverview());
        movie.setPosterPath(details.getPosterPath());

        if (details.getReleaseDate() != null && !details.getReleaseDate().isEmpty()) {
            try {
                int year = LocalDate.parse(details.getReleaseDate()).getYear();
                movie.setReleaseYear(year);
            } catch (Exception ignored) {
                // skip invalid dates
            }
        }

        if (details.getGenres() != null && !details.getGenres().isEmpty()) {
            String genres = details.getGenres().stream()
                    .map(Genre::getName)
                    .collect(Collectors.joining(", "));
            movie.setGenre(genres);
        }

        return movieRepository.save(movie);
    }
}
