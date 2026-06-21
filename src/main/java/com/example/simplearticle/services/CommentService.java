package com.example.simplearticle.services;

import com.example.simplearticle.requests.CommentRequest;
import com.example.simplearticle.response.CommentResponse;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    List<CommentResponse> findByArticle(Long articleId);
    CommentResponse findById(UUID id);
    CommentResponse create(Long articleId, CommentRequest payload);
    CommentResponse update(Long articleId, UUID commentId, CommentRequest payload);
    void delete(Long articleId, UUID commentId);
}
