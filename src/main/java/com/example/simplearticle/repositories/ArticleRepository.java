package com.example.simplearticle.repositories;

import com.example.simplearticle.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
