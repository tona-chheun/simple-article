package com.example.simplearticle.mappers;

import com.example.simplearticle.models.Article;
import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.response.ArticleResponse;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class ArticleMapper implements DataMapper<Article, ArticleRequest, ArticleResponse>{
    public ArticleResponse toResponse(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt().atOffset(ZoneOffset.UTC),
                article.getUpdatedAt().atOffset(ZoneOffset.UTC)
        );
    }

    public Article toEntity(ArticleRequest payload) {
        Article article = new Article();
        article.setTitle(payload.title());
        article.setContent(payload.content());

        return article;
    }
}
