package com.example.simplearticle.services;

import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.response.ApiResponse;
import com.example.simplearticle.response.ArticlePageResponse;
import com.example.simplearticle.response.ArticleResponse;

public interface ArticleService {
    ArticlePageResponse getAll(int page, int size);
    ApiResponse<ArticleResponse> findById(Long id);
    ArticleResponse create(ArticleRequest articleRequest);
    ArticleResponse update(Long id, ArticleRequest articleRequest);
    void delete(Long id);
}
