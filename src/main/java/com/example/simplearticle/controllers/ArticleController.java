package com.example.simplearticle.controllers;

import com.example.simplearticle.models.Article;
import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.services.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getAll() {
        return this.articleService.getAll();
    }

    @PostMapping
    public Article create(@RequestBody ArticleRequest articleRequest) {
        return this.articleService.create(articleRequest);
    }
}
