package com.example.articleservice.controllers;

import com.example.articleservice.models.Article;
import com.example.articleservice.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService service;

    @GetMapping
    public List<Article> getAll() {
        return service.findAll();
    }

    @GetMapping("create")
    public Article create() {
        return service.create();
    }
}
