package com.quierlinn.filmpicker.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String message;

    public AuthResponse(String token) {
        this.token = token;
        this.message = "Authentication successful";
    }
}
