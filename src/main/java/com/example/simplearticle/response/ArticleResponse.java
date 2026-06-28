package com.example.simplearticle.response;

import java.time.OffsetDateTime;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
