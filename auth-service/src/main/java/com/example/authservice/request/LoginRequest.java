package com.example.authservice.request;

public record LoginRequest(
        String email,
        String password
) {
}
