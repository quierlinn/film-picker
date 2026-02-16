package com.quierlinn.filmpicker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MovieListRequest {
    @NotBlank
    private String name;
}
