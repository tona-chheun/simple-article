package com.example.simplearticle.services;

import com.example.simplearticle.models.Article;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Override
    public List<Article> getAll() {
        // return List.of(new Article(1L, "Java programming", "java is popular on web application"));
        return Arrays.asList(
                new Article(1L, "Java programming", "java is powerful on web application"),
                new Article(2L, "NodeJs programming", "nodeJs is popular on web application")
        );
    }
}
