package com.example.simplearticle.services;

import com.example.simplearticle.requests.CommentRequest;
import com.example.simplearticle.response.CommentResponse;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    List<CommentResponse> findByArticle(Long articleId);
    CommentResponse create(Long articleId, CommentRequest request);
    void delete(Long articleId, UUID commentId);
}
