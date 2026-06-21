package com.example.simplearticle.repositories;

import com.example.simplearticle.models.Article;
import com.example.simplearticle.models.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("Find comments by article")
    void findCommentsByArticle() {
        Article savedArticle = articleRepository.save(mockArticle());

        Comment comment = new Comment();
        comment.setContent("Nice post");
        comment.setArticle(savedArticle);
        commentRepository.save(comment);

        List<Comment> comments =
                commentRepository.findByArticleIdAndDeletedAtIsNull(savedArticle.getId());

        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).getContent()).isEqualTo("Nice post");
    }

    @Test
    @DisplayName("Create comment")
    void createComment() {
        Article savedArticle = articleRepository.save(mockArticle());

        Comment comment = new Comment();
        comment.setContent("Good article");
        comment.setArticle(savedArticle);

        Comment savedComment = commentRepository.save(comment);

        assertThat(savedComment.getId()).isNotNull();
        assertThat(savedComment.getContent()).isEqualTo("Good article");
        assertThat(savedComment.getArticle().getId()).isEqualTo(savedArticle.getId());
    }

    @Test
    @DisplayName("Update comment")
    void updateComment() {
        Article savedArticle = articleRepository.save(mockArticle());

        Comment comment = new Comment();
        comment.setContent("Old comment");
        comment.setArticle(savedArticle);

        Comment savedComment = commentRepository.save(comment);

        savedComment.setContent("Updated comment");
        Comment updatedComment = commentRepository.save(savedComment);

        assertThat(updatedComment.getContent()).isEqualTo("Updated comment");
    }

    @Test
    @DisplayName("Soft delete comment")
    void softDeleteComment() {
        Article savedArticle = articleRepository.save(mockArticle());

        Comment comment = new Comment();
        comment.setContent("Comment to delete");
        comment.setArticle(savedArticle);

        Comment savedComment = commentRepository.save(comment);

        savedComment.setDeletedAt(LocalDateTime.now());
        commentRepository.save(savedComment);

        List<Comment> comments =
                commentRepository.findByArticleIdAndDeletedAtIsNull(savedArticle.getId());

        assertThat(comments).isEmpty();
    }

    private Article mockArticle() {
        Article article = new Article();
        article.setTitle("Spring Boot");
        article.setContent("Spring Boot content");
        return article;
    }
}
