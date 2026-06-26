package com.example.articleservice.services;

import com.example.articleservice.models.Article;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Override
    public List<Article> findAll() {
        return Arrays.asList(
                new Article(1L, "Java", "spring is popular using web application"),
                new Article(2L, "python", "python is powerful using AI")
        );
    }
}
