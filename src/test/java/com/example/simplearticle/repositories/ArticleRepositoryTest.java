package com.example.simplearticle.repositories;

import com.example.simplearticle.models.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void shouldSaveArticle() {

        Article article = new Article();
        article.setTitle("Java");
        article.setContent("Java Content");

        Article saved = articleRepository.save(article);

        assertNotNull(saved.getId());
    }

    @Test
    void shouldFindActiveArticle() {

        Article article = new Article();
        article.setTitle("Spring");
        article.setContent("Spring Content");

        articleRepository.save(article);

        Optional<Article> result =
                articleRepository.findByIdAndDeletedAtIsNull(
                        article.getId()
                );

        assertTrue(result.isPresent());
    }

    @Test
    void shouldNotReturnSoftDeletedArticle() {

        Article article = new Article();
        article.setTitle("Deleted");
        article.setDeletedAt(LocalDateTime.now());

        articleRepository.save(article);

        Optional<Article> result =
                articleRepository.findByIdAndDeletedAtIsNull(
                        article.getId()
                );

        assertFalse(result.isPresent());
    }
}
