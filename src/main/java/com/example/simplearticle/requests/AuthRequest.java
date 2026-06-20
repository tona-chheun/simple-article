package com.example.simplearticle.requests;

public record AuthRequest(
        String email,
        String password
) {
}
