package com.example.simplearticle.controllers;

import com.example.simplearticle.requests.CommentRequest;
import com.example.simplearticle.response.ApiResponse;
import com.example.simplearticle.response.CommentResponse;
import com.example.simplearticle.services.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/articles/{articleId}/comments")
@SecurityRequirement(name = "ApiKeyAuth")
@SecurityRequirement(name = "bearerAuth")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(
            @PathVariable Long articleId) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Comments retrieved successfully",
                        commentService.findByArticle(articleId)
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> create(
            @PathVariable Long articleId,
            @Valid @RequestBody CommentRequest request) {

        CommentResponse comment =
                commentService.create(articleId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Comment created successfully",
                        comment
                )
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long articleId,
            @PathVariable UUID commentId) {

        commentService.delete(articleId, commentId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Comment deleted successfully",
                        null
                )
        );
    }
}
