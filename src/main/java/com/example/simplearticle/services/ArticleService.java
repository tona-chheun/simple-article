package com.example.simplearticle.services;

import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.response.ArticleResponse;

import java.util.List;

public interface ArticleService {
    List<ArticleResponse> getAll();
    ArticleResponse findById(Long id);
    ArticleResponse create(ArticleRequest articleRequest);
    ArticleResponse update(Long id, ArticleRequest articleRequest);
    void delete(Long id);
}
