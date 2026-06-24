package com.example.simplearticle.services;

import com.example.simplearticle.soap.ArticleResponse;
import com.example.simplearticle.soap.CreateArticleRequest;
import com.example.simplearticle.soap.GetAllArticlesResponse;
import com.example.simplearticle.soap.UpdateArticleRequest;

public interface ArticleService {
    GetAllArticlesResponse getAll();
    ArticleResponse findById(Long id);
    ArticleResponse create(CreateArticleRequest request);
    ArticleResponse update(UpdateArticleRequest request);
    void delete(Long id);
}
