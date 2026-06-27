package com.example.articleservice.services;

import com.example.articleservice.models.Article;

public interface ArticleEventPublisher {
    void publishArticleCreated(Article article);
}
