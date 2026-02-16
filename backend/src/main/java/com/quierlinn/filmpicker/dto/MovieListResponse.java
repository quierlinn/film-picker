package com.quierlinn.filmpicker.dto;

import com.quierlinn.filmpicker.entity.Movie;
import lombok.Data;

import java.util.List;

@Data
public class MovieListResponse {
    private Long id;
    private String name;
    private List<Movie> movies;
}
