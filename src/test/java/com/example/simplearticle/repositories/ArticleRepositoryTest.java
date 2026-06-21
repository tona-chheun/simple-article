package com.example.simplearticle.repositories;

import com.example.simplearticle.models.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("Should create article")
    void shouldCreateArticle() {
        Article article = new Article();
        article.setTitle("Java");
        article.setContent("Java content");

        Article saved = articleRepository.save(article);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Java");
        assertThat(saved.getContent()).isEqualTo("Java content");
    }

    @Test
    @DisplayName("Should find article by id")
    void shouldFindArticleById() {
        Article article = new Article();
        article.setTitle("Java");
        article.setContent("Java Content");
        Article articleSaved = articleRepository.save(article);

        Optional<Article> found = articleRepository.findById(articleSaved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Java");
    }

    @Test
    @DisplayName("Should find only articles not soft deleted")
    void shouldFindByDeletedAtIsNull() {
        Article article = new Article();
        article.setTitle("Java");
        article.setContent("Java Content");
        Article active = articleRepository.save(article);

        Article deleted = new Article();
        deleted.setTitle("Deleted Article");
        deleted.setContent("Deleted content");
        deleted.setDeletedAt(LocalDateTime.now());
        articleRepository.save(deleted);

        List<Article> articles = articleRepository.findByDeletedAtIsNull();

        assertThat(articles).hasSize(1);
        assertThat(articles.get(0).getId()).isEqualTo(active.getId());
    }

    @Test
    @DisplayName("Should find by id and deletedAt is null")
    void shouldFindByIdAndDeletedAtIsNull() {
        Article article = new Article();
        article.setTitle("Find Me");
        article.setContent("Content");
        Article articleSaved = articleRepository.save(article);

        Optional<Article> found =
                articleRepository.findByIdAndDeletedAtIsNull(articleSaved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Find Me");
    }

    @Test
    @DisplayName("Should not find soft deleted article by id")
    void shouldNotFindSoftDeletedArticleById() {
        Article article = new Article();
        article.setTitle("Soft Deleted");
        article.setContent("Content");
        article.setDeletedAt(LocalDateTime.now());

        Article saved = articleRepository.save(article);

        Optional<Article> found =
                articleRepository.findByIdAndDeletedAtIsNull(saved.getId());

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should update article")
    void shouldUpdateArticle() {
        Article article = new Article();
        article.setTitle("Old Title");
        article.setContent("Old Content");
        Article articleSaved = articleRepository.save(article);

        articleSaved.setTitle("New Title");
        articleSaved.setContent("New Content");

        Article updated = articleRepository.save(articleSaved);

        assertThat(updated.getTitle()).isEqualTo("New Title");
        assertThat(updated.getContent()).isEqualTo("New Content");
    }

    @Test
    @DisplayName("Should delete article permanently")
    void shouldDeleteArticle() {
        Article article = new Article();
        article.setTitle("Delete Me");
        article.setContent("Content");
        Article articleSaved = articleRepository.save(article);

        articleRepository.deleteById(articleSaved.getId());

        Optional<Article> found = articleRepository.findById(articleSaved.getId());

        assertThat(found).isEmpty();
    }
}
