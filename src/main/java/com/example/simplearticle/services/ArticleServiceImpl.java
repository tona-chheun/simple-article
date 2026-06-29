package com.example.simplearticle.services;

import com.example.simplearticle.exceptions.RecordNotFoundException;
import com.example.simplearticle.mappers.ArticleMapper;
import com.example.simplearticle.models.Article;
import com.example.simplearticle.repositories.ArticleRepository;
import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.response.ArticleResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ArticleAclService articleAclService;

    public ArticleServiceImpl(
            ArticleRepository articleRepository,
            ArticleMapper articleMapper,
            ArticleAclService articleAclService
            ) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.articleAclService = articleAclService;
    }

    @PreAuthorize("hasPermission(#id, 'com.example.simplearticle.models.Article', 'READ')")
    @Override
    public List<ArticleResponse> getAll() {
        return this.articleRepository.findByDeletedAtIsNull()
                .stream()
                .map(articleMapper::toResponse)
                .toList();
    }

    @PreAuthorize("hasPermission(#id, 'com.example.simplearticle.models.Article', 'READ')")
    @Override
    public ArticleResponse findById(Long id) {
        Article article = this.articleRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new RecordNotFoundException(id));
        return this.articleMapper.toResponse(article);
    }

    @Transactional
    @Override
    public ArticleResponse create(ArticleRequest payload, Authentication auth) {
        Article article = this.articleRepository.save(this.articleMapper.toEntity(payload));
        articleAclService.createAclForArticle(article.getId(), auth.getName());
        return this.articleMapper.toResponse(article);
    }

    @PreAuthorize("hasPermission(#id, 'com.example.simplearticle.models.Article', 'WRITE')")
    @Override
    public ArticleResponse update(Long id, ArticleRequest payload) {
        Article article = articleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        article.setTitle(payload.title());
        article.setContent(payload.content());
        return this.articleMapper.toResponse(this.articleRepository.save(article));
    }

    @PreAuthorize("hasPermission(#id, 'com.example.simplearticle.models.Article', 'DELETE')")
    @Override
    public void delete(Long id) {
        Article article = articleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        article.setDeletedAt(LocalDateTime.now());
        articleRepository.save(article);
    }
}
