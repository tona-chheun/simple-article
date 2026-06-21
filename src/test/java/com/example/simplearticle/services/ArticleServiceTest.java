package com.example.simplearticle.services;

import com.example.simplearticle.exceptions.RecordNotFoundException;
import com.example.simplearticle.mappers.ArticleMapper;
import com.example.simplearticle.models.Article;
import com.example.simplearticle.repositories.ArticleRepository;
import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.response.ArticleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleMapper articleMapper;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private Article article;
    private ArticleResponse articleResponse;

    @BeforeEach
    void setUp() {

        article = new Article();
        article.setId(1L);
        article.setTitle("sports");
        article.setContent("sports is most popular worldwide");

        articleResponse = new ArticleResponse(
                1L,
                "sports",
                "sports is most popular worldwide",
                LocalDateTime.of(2026, 6, 21, 10, 40, 10),
                LocalDateTime.of(2026, 6, 21, 10, 50, 30)
        );
    }

    @Test
    void shouldGetAllArticles() {
        when(articleRepository.findByDeletedAtIsNull())
                .thenReturn(List.of(article));

        when(articleMapper.toResponse(article))
                .thenReturn(articleResponse);

        List<ArticleResponse> result = articleService.getAll();

        assertEquals(1, result.size());
        assertEquals("sports", result.getFirst().title());

        verify(articleRepository).findByDeletedAtIsNull();
    }

    @Test
    void shouldFindArticleById() {
        when(articleRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(article));

        when(articleMapper.toResponse(article))
                .thenReturn(articleResponse);

        ArticleResponse result = articleService.findById(1L);

        assertEquals(1L, result.id());
        assertEquals("sports", result.title());

        verify(articleRepository).findByIdAndDeletedAtIsNull(1L);
    }

    @Test
    void shouldThrowExceptionWhenArticleNotFound() {
        when(articleRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RecordNotFoundException.class,
                () -> articleService.findById(1L)
        );
    }

    @Test
    void shouldCreateArticle() {
        ArticleRequest request = new ArticleRequest(
                "New Article",
                "New Content"
        );

        Article newArticle = new Article();
        newArticle.setTitle(request.title());
        newArticle.setContent(request.content());

        Article savedArticle = new Article();
        savedArticle.setId(2L);
        savedArticle.setTitle(request.title());
        savedArticle.setContent(request.content());

        ArticleResponse response = new ArticleResponse(
                2L,
                "New Article",
                "New Content",
                LocalDateTime.of(2026, 6, 21, 11, 50, 30),
                LocalDateTime.of(2026, 6, 21, 11, 50, 30)
        );

        when(articleMapper.toEntity(request)).thenReturn(newArticle);
        when(articleRepository.save(newArticle)).thenReturn(savedArticle);
        when(articleMapper.toResponse(savedArticle)).thenReturn(response);

        ArticleResponse result = articleService.create(request);

        assertEquals(2L, result.id());
        assertEquals("New Article", result.title());

        verify(articleRepository).save(newArticle);
    }

    @Test
    void shouldUpdateArticle() {
        ArticleRequest request = new ArticleRequest(
                "Updated Title",
                "Updated Content"
        );

        Article updatedArticle = new Article();
        updatedArticle.setId(1L);
        updatedArticle.setTitle("Updated Title");
        updatedArticle.setContent("Updated Content");

        ArticleResponse response = new ArticleResponse(
                1L,
                "Updated Title",
                "Updated Content",
                LocalDateTime.of(2026, 6, 21, 12, 10, 30),
                LocalDateTime.of(2026, 6, 21, 12, 10, 30)
        );

        when(articleRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(article));

        when(articleRepository.save(article))
                .thenReturn(updatedArticle);

        when(articleMapper.toResponse(updatedArticle))
                .thenReturn(response);

        ArticleResponse result = articleService.update(1L, request);

        assertEquals("Updated Title", result.title());
        assertEquals("Updated Content", result.content());

        verify(articleRepository).save(article);
    }

    @Test
    void shouldDeleteArticleSoftDelete() {
        when(articleRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(article));

        articleService.delete(1L);

        assertNotNull(article.getDeletedAt());

        verify(articleRepository).save(article);
    }

    @Test
    void shouldThrowWhenArticleNotFound() {
        when(articleRepository.findByIdAndDeletedAtIsNull(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                RecordNotFoundException.class,
                () -> articleService.delete(99L)
        );

        verify(articleRepository, never()).save(any());
    }
}
