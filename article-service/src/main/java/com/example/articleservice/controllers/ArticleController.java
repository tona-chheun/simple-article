package com.example.articleservice.controllers;

import com.example.articleservice.models.Article;
import com.example.articleservice.services.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("articles")
@RequiredArgsConstructor
@Tag(name = "Articles", description = "Article management APIs")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {

    private final ArticleService service;

    @Operation(summary = "Get all articles")
    @GetMapping
    public List<Article> getAll() {
        return service.findAll();
    }

    @Operation(summary = "Create default article")
    @GetMapping("create")
    public Article create() {
        return service.create();
    }
}
