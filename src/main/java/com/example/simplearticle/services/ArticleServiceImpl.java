package com.example.simplearticle.services;

import com.example.simplearticle.annotations.Auditable;
import com.example.simplearticle.exceptions.RecordNotFoundException;
import com.example.simplearticle.mappers.ArticleMapper;
import com.example.simplearticle.models.Article;
import com.example.simplearticle.repositories.ArticleRepository;
import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.response.ArticleResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(
            ArticleRepository articleRepository,
            ArticleMapper articleMapper
    ) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    public List<ArticleResponse> getAll() {
        return this.articleRepository.findByDeletedAtIsNull()
                .stream()
                .map(articleMapper::toResponse)
                .toList();
    }

    @Override
    public ArticleResponse findById(Long id) {
        Article article = this.articleRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new RecordNotFoundException(id));
        return this.articleMapper.toResponse(article);
    }

    @Auditable(action = "CREATE", module = "ARTICLE")
    @Override
    public ArticleResponse create(ArticleRequest payload) {
        Article article = this.articleRepository.save(this.articleMapper.toEntity(payload));
        return this.articleMapper.toResponse(article);
    }

    @Auditable(action = "UPDATE", module = "ARTICLE")
    @Override
    public ArticleResponse update(Long id, ArticleRequest payload) {
        Article article = articleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        article.setTitle(payload.title());
        article.setContent(payload.content());
        return this.articleMapper.toResponse(this.articleRepository.save(article));
    }

    @Auditable(action = "DELETE", module = "ARTICLE")
    @Override
    public void delete(Long id) {
        Article article = articleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        article.setDeletedAt(LocalDateTime.now());
        articleRepository.save(article);
    }
}
