package com.quierlinn.filmpicker.dto.tmdb;

import lombok.Data;

import java.util.List;

@Data
public class TmdbSearchResponse {
    private List<TmdbSearchResult> results;
}
