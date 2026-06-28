package com.example.simplearticle.repositories;

import com.example.simplearticle.models.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByDeletedAtIsNull(Pageable pageable);
    Optional<Article> findByIdAndDeletedAtIsNull(Long id);
}
