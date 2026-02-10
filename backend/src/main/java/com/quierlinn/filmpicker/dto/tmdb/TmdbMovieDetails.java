package com.quierlinn.filmpicker.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TmdbMovieDetails {
    private Integer id;
    private String title;
    @JsonProperty("release_date")
    private String releaseDate;
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;

    private List<Genre> genres;
}
