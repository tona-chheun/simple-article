package com.example.simplearticle.services;

public interface ArticleAclService {
    void createAclForArticle(Long articleId, String username);
}
