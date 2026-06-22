package com.example.simplearticle.controllers;

import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.response.ApiResponse;
import com.example.simplearticle.response.ArticleResponse;
import com.example.simplearticle.services.ArticleService;
import jakarta.validation.Valid;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {
    private final ArticleService articleService;

    private final JobOperator jobOperator;
    private final Job importArticleJob;

    public ArticleController(
            ArticleService articleService,
            JobOperator jobOperator,
            Job importArticleJob
    ) {
        this.articleService = articleService;

        this.jobOperator = jobOperator;
        this.importArticleJob = importArticleJob;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> getAll() {
        List<ArticleResponse> articles =  this.articleService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Articles retrieved successfully",
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

    @PostMapping(
            value = "/import",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<String>> importArticles(@RequestParam("file") MultipartFile file) throws Exception {

        Path uploadDir = Paths.get("uploads");
        Files.createDirectories(uploadDir);

        Path filePath = uploadDir.resolve(
                System.currentTimeMillis() + "_" + file.getOriginalFilename()
        );

        file.transferTo(filePath);
        JobParameters params = new JobParametersBuilder()
                .addString("filePath", filePath.toAbsolutePath().toString())
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution execution =
                jobOperator.start(importArticleJob, params);

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Job started with execution id: " + execution.getId(),
                null
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
    public ResponseEntity<ApiResponse<ArticleResponse>> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Article deleted successfully",
                null
        ));
    }
}
