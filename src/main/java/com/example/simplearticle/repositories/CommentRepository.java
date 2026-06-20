package com.example.simplearticle.repositories;

import com.example.simplearticle.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByArticleIdAndDeletedAtIsNull(Long articleId);
    Optional<Comment> findByIdAndDeletedAtIsNull(UUID id);
    Optional<Comment> findByIdAndArticleIdAndDeletedAtIsNull(
            UUID commentId,
            Long articleId
    );
}
