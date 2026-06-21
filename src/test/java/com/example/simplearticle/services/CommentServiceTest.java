package com.example.simplearticle.services;

import com.example.simplearticle.exceptions.RecordNotFoundException;
import com.example.simplearticle.mappers.CommentMapper;
import com.example.simplearticle.models.Article;
import com.example.simplearticle.models.Comment;
import com.example.simplearticle.repositories.ArticleRepository;
import com.example.simplearticle.repositories.CommentRepository;
import com.example.simplearticle.requests.CommentRequest;
import com.example.simplearticle.response.ArticleResponse;
import com.example.simplearticle.response.CommentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Article article;
    private ArticleResponse articleResponse;
    private Comment comment;
    private CommentResponse commentResponse;
    private CommentRequest commentRequest;

    @BeforeEach
    void setUp() {
        article = new Article();
        article.setId(1L);
        article.setTitle("Java");
        article.setContent("Java content");

        articleResponse = new ArticleResponse(
                1L,
                "Java",
                "Java content",
                LocalDateTime.of(2026, 6, 21, 10, 40, 10),
                LocalDateTime.of(2026, 6, 21, 10, 50, 30)
        );

        comment = new Comment();
        comment.setId(UUID.randomUUID());
        comment.setContent("Good article");
        comment.setArticle(article);

        commentRequest = new CommentRequest("Good article");

        commentResponse = new CommentResponse(
                comment.getId(),
                comment.getContent(),
                LocalDateTime.of(2026, 6, 21, 10, 40, 10),
                LocalDateTime.of(2026, 6, 21, 10, 50, 30),
                articleResponse
        );
    }

    @Test
    void shouldFindCommentsByArticle() {
        when(commentRepository.findByArticleIdAndDeletedAtIsNull(1L))
                .thenReturn(List.of(comment));

        when(commentMapper.toResponse(comment))
                .thenReturn(commentResponse);

        List<CommentResponse> result = commentService.findByArticle(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).content()).isEqualTo("Good article");

        verify(commentRepository).findByArticleIdAndDeletedAtIsNull(1L);
        verify(commentMapper).toResponse(comment);
    }

    @Test
    void shouldFindCommentById() {
        when(commentRepository.findByIdAndDeletedAtIsNull(comment.getId()))
                .thenReturn(Optional.of(comment));

        when(commentMapper.toResponse(comment))
                .thenReturn(commentResponse);

        CommentResponse result = commentService.findById(comment.getId());

        assertNotNull(result);
        assertEquals(comment.getId(), result.id());
        assertEquals("Good article", result.content());

        verify(commentRepository).findByIdAndDeletedAtIsNull(comment.getId());
        verify(commentMapper).toResponse(comment);
    }

    @Test
    void shouldThrowExceptionWhenFindCommentByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(commentRepository.findByIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());

        assertThrows(
                RecordNotFoundException.class,
                () -> commentService.findById(id)
        );

        verify(commentRepository).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    void shouldCreateComment() {
        when(articleRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(article));

        when(commentMapper.toEntity(commentRequest, article))
                .thenReturn(comment);

        when(commentRepository.save(comment))
                .thenReturn(comment);

        when(commentMapper.toResponse(comment))
                .thenReturn(commentResponse);

        CommentResponse result = commentService.create(1L, commentRequest);

        assertNotNull(result);
        assertEquals("Good article", result.content());

        verify(articleRepository).findByIdAndDeletedAtIsNull(1L);
        verify(commentMapper).toEntity(commentRequest, article);
        verify(commentRepository).save(comment);
        verify(commentMapper).toResponse(comment);
    }


    @Test
    void shouldThrowExceptionWhenCreateCommentWithArticleNotFound() {
        when(articleRepository.findByIdAndDeletedAtIsNull(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.create(99L, commentRequest))
                .isInstanceOf(RecordNotFoundException.class);

        verify(articleRepository).findByIdAndDeletedAtIsNull(99L);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void shouldUpdateComment() {
        CommentRequest updateRequest = new CommentRequest("Updated comment");

        Comment updatedComment = new Comment();
        updatedComment.setId(comment.getId());
        updatedComment.setContent("Updated comment");
        updatedComment.setArticle(article);

        CommentResponse updatedResponse = new CommentResponse(
                comment.getId(),
                "Updated comment",
                LocalDateTime.of(2026, 6, 21, 10, 40, 10),
                LocalDateTime.of(2026, 6, 21, 10, 50, 30),
                articleResponse
        );

        when(articleRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(article));

        when(commentRepository.findByIdAndDeletedAtIsNull(comment.getId()))
                .thenReturn(Optional.of(comment));

        when(commentRepository.save(comment))
                .thenReturn(updatedComment);

        when(commentMapper.toResponse(updatedComment))
                .thenReturn(updatedResponse);

        CommentResponse result =
                commentService.update(1L, comment.getId(), updateRequest);

        assertNotNull(result);
        assertEquals("Updated comment", result.content());

        verify(articleRepository).findByIdAndDeletedAtIsNull(1L);
        verify(commentRepository).findByIdAndDeletedAtIsNull(comment.getId());
        verify(commentRepository).save(comment);
        verify(commentMapper).toResponse(updatedComment);
    }

    @Test
    void shouldThrowExceptionWhenUpdateArticleNotFound() {
        CommentRequest updateRequest = new CommentRequest("Updated comment");

        when(articleRepository.findByIdAndDeletedAtIsNull(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                RecordNotFoundException.class,
                () -> commentService.update(99L, comment.getId(), updateRequest)
        );

        verify(articleRepository).findByIdAndDeletedAtIsNull(99L);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdateCommentNotFound() {
        UUID commentId = UUID.randomUUID();

        when(articleRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(article));

        when(commentRepository.findByIdAndDeletedAtIsNull(commentId))
                .thenReturn(Optional.empty());

        assertThrows(
                RecordNotFoundException.class,
                () -> commentService.update(1L, commentId, commentRequest)
        );

        verify(articleRepository).findByIdAndDeletedAtIsNull(1L);
        verify(commentRepository).findByIdAndDeletedAtIsNull(commentId);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void shouldDeleteComment() {
        when(commentRepository.findByIdAndArticleIdAndDeletedAtIsNull(
                comment.getId(),
                1L
        )).thenReturn(Optional.of(comment));

        commentService.delete(1L, comment.getId());

        assertNotNull(comment.getDeletedAt());

        verify(commentRepository).findByIdAndArticleIdAndDeletedAtIsNull(
                comment.getId(),
                1L
        );
        verify(commentRepository).save(comment);
    }

    @Test
    void shouldThrowExceptionWhenDeleteCommentNotFound() {
        UUID commentId = UUID.randomUUID();

        when(commentRepository.findByIdAndArticleIdAndDeletedAtIsNull(
                commentId,
                1L
        )).thenReturn(Optional.empty());

        assertThrows(
                RecordNotFoundException.class,
                () -> commentService.delete(1L, commentId)
        );

        verify(commentRepository).findByIdAndArticleIdAndDeletedAtIsNull(
                commentId,
                1L
        );
        verify(commentRepository, never()).save(any(Comment.class));
    }
}
