package com.example.simplearticle.repositories;

import com.example.simplearticle.models.Article;
import jakarta.validation.constraints.NotNull;
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
        Article saved = articleRepository.save(mockArticle("Java", "Java content"));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Java");
        assertThat(saved.getContent()).isEqualTo("Java content");
    }

    @Test
    @DisplayName("Should find article by id")
    void shouldFindArticleById() {
        Article articleSaved = articleRepository.save(mockArticle("Java", "Java Content"));

        Optional<Article> found = articleRepository.findById(articleSaved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Java");
    }

    @Test
    @DisplayName("Should find only articles not soft deleted")
    void shouldFindByDeletedAtIsNull() {
        Article active = articleRepository.save(mockArticle("Java", "Java Content"));

        Article deleted = mockArticle("Deleted Article", "Deleted content");
        deleted.setDeletedAt(LocalDateTime.now());
        articleRepository.save(deleted);

        List<Article> articles = articleRepository.findByDeletedAtIsNull();

        assertThat(articles).hasSize(1);
        assertThat(articles.get(0).getId()).isEqualTo(active.getId());
    }

    @Test
    @DisplayName("Should find by id and deletedAt is null")
    void shouldFindByIdAndDeletedAtIsNull() {
        Article articleSaved = articleRepository.save(mockArticle("Find Me", "Content"));

        Optional<Article> found =
                articleRepository.findByIdAndDeletedAtIsNull(articleSaved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Find Me");
    }

    @Test
    @DisplayName("Should not find soft deleted article by id")
    void shouldNotFindSoftDeletedArticleById() {
        Article article = mockArticle("Soft Deleted", "Content");
        article.setDeletedAt(LocalDateTime.now());

        Article saved = articleRepository.save(article);

        Optional<Article> found =
                articleRepository.findByIdAndDeletedAtIsNull(saved.getId());

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should update article")
    void shouldUpdateArticle() {
        Article articleSaved = articleRepository.save(mockArticle("Old Title", "Old Content"));

        articleSaved.setTitle("New Title");
        articleSaved.setContent("New Content");

        Article updated = articleRepository.save(articleSaved);

        assertThat(updated.getTitle()).isEqualTo("New Title");
        assertThat(updated.getContent()).isEqualTo("New Content");
    }

    @Test
    @DisplayName("Should delete article permanently")
    void shouldDeleteArticle() {
        Article articleSaved = articleRepository.save(mockArticle("Delete Me", "Content"));

        articleRepository.deleteById(articleSaved.getId());

        Optional<Article> found = articleRepository.findById(articleSaved.getId());

        assertThat(found).isEmpty();
    }

    private Article mockArticle(String title, String content) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        return article;
    }
}
