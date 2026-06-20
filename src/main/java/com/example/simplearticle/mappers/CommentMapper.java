package com.example.simplearticle.mappers;

import com.example.simplearticle.models.Article;
import com.example.simplearticle.models.Comment;
import com.example.simplearticle.requests.CommentRequest;
import com.example.simplearticle.response.CommentResponse;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper implements DataMapper<Comment, CommentRequest, CommentResponse> {
    private final ArticleMapper articleMapper;

    public CommentMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public Comment toEntity(CommentRequest payload) {
        Comment comment = new Comment();
        comment.setContent(payload.content());
        return comment;
    }

    public Comment toEntity(CommentRequest payload, Article article) {
        Comment comment = new Comment();
        comment.setContent(payload.content());
        comment.setArticle(article);
        return comment;
    }

    @Override
    public CommentResponse toResponse(Comment entity) {
        return new CommentResponse(
                entity.getId(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                articleMapper.toResponse(entity.getArticle())
        );
    }
}
