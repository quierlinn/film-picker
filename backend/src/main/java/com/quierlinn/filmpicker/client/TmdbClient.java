package com.quierlinn.filmpicker.client;

import com.quierlinn.filmpicker.dto.tmdb.TmdbMovieDetails;
import com.quierlinn.filmpicker.dto.tmdb.TmdbSearchResponse;
import com.quierlinn.filmpicker.dto.tmdb.TmdbSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TmdbClient {

    private final RestTemplate restTemplate;
    @Value("${tmdb.api-key}")
    private String apiKey;

    private static final String BASE_URL = "https://api.themoviedb.org/3";

    public List<TmdbSearchResult> searchMovies(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/search/movie")
                .queryParam("query", query)
                .queryParam("api_key", apiKey)
                .toUriString();

        ResponseEntity<TmdbSearchResponse> response = restTemplate.getForEntity(url, TmdbSearchResponse.class);
        return response.getBody().getResults();
    }

    public TmdbMovieDetails getMovieDetails(Integer tmdbId) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/movie/" + tmdbId)
                .queryParam("api_key", apiKey)
                .toUriString();

        ResponseEntity<TmdbMovieDetails> response = restTemplate.getForEntity(url, TmdbMovieDetails.class);
        return response.getBody();
    }
}
