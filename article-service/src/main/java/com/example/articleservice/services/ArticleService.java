package com.example.articleservice.services;

import com.example.articleservice.models.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findAll();
    Article create();
}
