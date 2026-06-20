package com.example.simplearticle.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponse(
        UUID id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ArticleResponse article
) {
}
