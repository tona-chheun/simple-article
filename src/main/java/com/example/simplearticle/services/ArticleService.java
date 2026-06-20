package com.example.simplearticle.services;

import com.example.simplearticle.models.Article;
import com.example.simplearticle.requests.ArticleRequest;

import java.util.List;

public interface ArticleService {
    List<Article> getAll();
    Article create(ArticleRequest articleRequest);
}
