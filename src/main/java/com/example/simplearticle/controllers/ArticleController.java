package com.example.simplearticle.controllers;

import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.response.ApiResponse;
import com.example.simplearticle.response.ArticleResponse;
import com.example.simplearticle.services.ArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/articles")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> getAll() {
        List<ArticleResponse> articles =  this.articleService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Article retrieved successfully",
                articles
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleResponse>> findById(@PathVariable Long id) {
        ArticleResponse article = this.articleService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Article retrieved successfully",
                article
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ArticleResponse>> create(@Valid @RequestBody ArticleRequest payload) {
        ArticleResponse article = this.articleService.create(payload);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Article created successfully",
                article
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest payload
    ) {
        ArticleResponse article = this.articleService.update(id, payload);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Article updated successfully",
                article
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        articleService.delete(id);
    }
}
