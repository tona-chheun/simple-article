package com.example.simplearticle.services;

import com.example.simplearticle.models.Article;
import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.response.ArticleResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ArticleService {
    List<ArticleResponse> getAll();
    ArticleResponse findById(Long id);
    ArticleResponse create(ArticleRequest articleRequest, Authentication auth);
    ArticleResponse update(Long id, ArticleRequest articleRequest);
    void delete(Long id);
}
