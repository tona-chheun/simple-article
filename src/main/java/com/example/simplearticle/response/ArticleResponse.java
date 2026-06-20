package com.example.simplearticle.response;

import java.time.LocalDateTime;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
