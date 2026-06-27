package com.example.articleservice.services;

import com.example.articleservice.models.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleEventPublisher articleEventPublisher;

    @Override
    public List<Article> findAll() {
        return Arrays.asList(
                new Article(1L, "Java", "spring is popular using web application"),
                new Article(2L, "python", "python is powerful using AI")
        );
    }

    @Override
    public Article create() {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("spring cloud demo");
        article.setContent("Manage communication and infrastructure between microservices.");
        articleEventPublisher.publishArticleCreated(article);
        return article;
    }
}
