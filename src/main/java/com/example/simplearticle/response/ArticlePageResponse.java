package com.example.simplearticle.response;

import java.util.List;

public record ArticlePageResponse(
        List<ArticleResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext,
        boolean hasPrevious
) {}
