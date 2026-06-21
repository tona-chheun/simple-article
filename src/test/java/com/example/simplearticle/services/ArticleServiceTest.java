package com.example.simplearticle.services;

import com.example.simplearticle.exceptions.RecordNotFoundException;
import com.example.simplearticle.mappers.ArticleMapper;
import com.example.simplearticle.models.Article;
import com.example.simplearticle.repositories.ArticleRepository;
import com.example.simplearticle.response.ArticleResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleMapper articleMapper;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    void shouldReturnArticleById() {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Spring Boot");

        ArticleResponse response =
                new ArticleResponse(1L, "Spring Boot", "Content", LocalDateTime.now(), LocalDateTime.now());

        when(articleRepository.findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(article));

        when(articleMapper.toResponse(article))
                .thenReturn(response);

        ArticleResponse result = articleService.findById(1L);

        assertEquals(1L, result.id());
        assertEquals("Spring Boot", result.title());
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
}
