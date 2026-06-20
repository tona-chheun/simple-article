package com.example.simplearticle.response;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {}
