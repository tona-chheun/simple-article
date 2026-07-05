package com.example.simplearticle.services;

import com.example.simplearticle.models.Article;
import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.response.ArticleResponse;

import java.util.List;

public interface ArticleService {
    List<ArticleResponse> getAll();
    ArticleResponse findById(Long id);
    ArticleResponse create(ArticleRequest article);
    void update(Long id, ArticleRequest article);
    void delete(Long id);
}
