package com.example.simplearticle.services;

import com.example.simplearticle.exceptions.RecordNotFoundException;
import com.example.simplearticle.mappers.CommentMapper;
import com.example.simplearticle.models.Article;
import com.example.simplearticle.models.Comment;
import com.example.simplearticle.repositories.ArticleRepository;
import com.example.simplearticle.repositories.CommentRepository;
import com.example.simplearticle.requests.CommentRequest;
import com.example.simplearticle.response.CommentResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    public CommentServiceImpl(
            ArticleRepository articleRepository,
            CommentRepository commentRepository,
            CommentMapper commentMapper
    ) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;

        this.commentMapper = commentMapper;
    }

    @Override
    public List<CommentResponse> findByArticle(Long articleId) {
        return this.commentRepository.findByArticleIdAndDeletedAtIsNull(articleId)
                .stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    @Override
    public CommentResponse create(Long articleId, CommentRequest request) {
        Article article = articleRepository
                .findByIdAndDeletedAtIsNull(articleId)
                .orElseThrow(() -> new RecordNotFoundException(articleId));
        Comment comment = commentRepository.save(this.commentMapper.toEntity(request, article));
        return this.commentMapper.toResponse(comment);
    }

    @Override
    public void delete(Long articleId, UUID commentId) {
        Comment comment = commentRepository
                .findByIdAndArticleIdAndDeletedAtIsNull(
                        commentId,
                        articleId
                )
                .orElseThrow(() ->
                        new RecordNotFoundException(commentId));
        comment.setDeletedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }
}
