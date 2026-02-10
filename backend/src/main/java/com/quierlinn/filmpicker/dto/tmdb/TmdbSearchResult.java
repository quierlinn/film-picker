package com.quierlinn.filmpicker.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TmdbSearchResult {
    private Integer id;
    private String title;
    @JsonProperty("release_date")
    private String releaseDate;
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;
}
