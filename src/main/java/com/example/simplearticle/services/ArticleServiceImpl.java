package com.example.simplearticle.services;

import com.example.simplearticle.exceptions.RecordNotFoundException;
import com.example.simplearticle.models.Article;
import com.example.simplearticle.repositories.ArticleRepository;
import com.example.simplearticle.soap.ArticleResponse;
import com.example.simplearticle.soap.CreateArticleRequest;
import com.example.simplearticle.soap.GetAllArticlesResponse;
import com.example.simplearticle.soap.UpdateArticleRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(
            ArticleRepository articleRepository
    ) {
        this.articleRepository = articleRepository;
    }

    @Override
    public GetAllArticlesResponse getAll() {
        List<ArticleResponse> articles = this.articleRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::mapResponse)
                .toList();
        GetAllArticlesResponse articlesResponse = new GetAllArticlesResponse();
        articlesResponse.setArticles(articles);
        return articlesResponse;
    }

    @Override
    public ArticleResponse findById(Long id) {
        Article article = this.articleRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new RecordNotFoundException(id));
        return mapResponse(article);
    }

    @Override
    public ArticleResponse create(CreateArticleRequest payload) {
        Article savedArticle = this.articleRepository.save(mapArticle(payload));
        return mapResponse(savedArticle);
    }

    @Override
    public ArticleResponse update(UpdateArticleRequest payload) {
        Article article = articleRepository.findByIdAndDeletedAtIsNull(payload.getId())
                .orElseThrow(() -> new RecordNotFoundException(payload.getId()));
        article.setTitle(payload.getTitle());
        article.setContent(payload.getContent());
        return mapResponse(this.articleRepository.save(article));
    }

    @Override
    public void delete(Long id) {
        Article article = articleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        article.setDeletedAt(LocalDateTime.now());
        articleRepository.save(article);
    }

    private Article mapArticle(CreateArticleRequest payload) {
        Article article = new Article();
        article.setTitle(payload.getTitle());
        article.setContent(payload.getContent());
        return article;
    }

    private ArticleResponse mapResponse(Article article) {
        ArticleResponse response = new ArticleResponse();
        response.setId(article.getId());
        response.setTitle(article.getTitle());
        response.setContent(article.getContent());
        return response;
    }
}
