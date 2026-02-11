package com.quierlinn.filmpicker.dto;

import com.quierlinn.filmpicker.entity.Movie;
import lombok.Data;

@Data
public class MovieWithRating {
    private final Movie movie;
    private final Double averageRating;
}
