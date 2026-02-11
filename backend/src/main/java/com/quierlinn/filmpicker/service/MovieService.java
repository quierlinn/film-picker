package com.quierlinn.filmpicker.service;

import com.quierlinn.filmpicker.client.TmdbClient;
import com.quierlinn.filmpicker.dto.MovieWithRating;
import com.quierlinn.filmpicker.dto.tmdb.Genre;
import com.quierlinn.filmpicker.dto.tmdb.TmdbSearchResult;
import com.quierlinn.filmpicker.entity.Movie;
import com.quierlinn.filmpicker.entity.Rating;
import com.quierlinn.filmpicker.entity.User;
import com.quierlinn.filmpicker.repository.MovieRepository;
import com.quierlinn.filmpicker.repository.RatingRepository;
import com.quierlinn.filmpicker.repository.UserRepository;
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
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

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

    public void rateMovie(Long movieId, Long userId, Integer score) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Rating rating = ratingRepository.findByUserIdAndMovieId(userId, movieId)
                .orElseGet(() -> {
                    Rating newRating = new Rating();
                    newRating.setUser(new User());
                    newRating.setMovie(movie);
                    return newRating;
                });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        rating.setUser(user);
        rating.setMovie(movie);
        rating.setScore(score);

        ratingRepository.save(rating);
    }

    public List<MovieWithRating> getTopRatedMovies(int limit) {
        List<Object[]> results = ratingRepository.findTopRatedMovies();
        return results.stream()
                .limit(limit)
                .map(obj -> new MovieWithRating((Movie) obj[0], (Double) obj[1]))
                .collect(Collectors.toList());
    }
}
