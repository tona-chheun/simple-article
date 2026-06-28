package com.example.simplearticle.services;

import com.example.simplearticle.exceptions.RecordNotFoundException;
import com.example.simplearticle.mappers.ArticleMapper;
import com.example.simplearticle.models.Article;
import com.example.simplearticle.repositories.ArticleRepository;
import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.response.ApiResponse;
import com.example.simplearticle.response.ArticlePageResponse;
import com.example.simplearticle.response.ArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ArticlePageResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        Page<Article> articlePage = this.articleRepository.findByDeletedAtIsNull(pageable);
        List<ArticleResponse> content = articlePage.getContent()
                .stream()
                .map(articleMapper::toResponse)
                .toList();
        return new ArticlePageResponse(
                content,
                articlePage.getNumber(),
                articlePage.getSize(),
                articlePage.getTotalElements(),
                articlePage.getTotalPages(),
                articlePage.hasNext(),
                articlePage.hasPrevious()
        );
    }

    @Override
    public ApiResponse<ArticleResponse> findById(Long id) {
        // return this.articleMapper.toResponse(article);
        return this.articleRepository.findByIdAndDeletedAtIsNull(id)
                .map(article -> new ApiResponse<ArticleResponse>(
                        true,
                        "Success",
                        articleMapper.toResponse(article)
                ))
                .orElse(new ApiResponse<>(false, "Record not found with id " + id, null));
    }

    @Override
    public ArticleResponse create(ArticleRequest payload) {
        Article article = this.articleRepository.save(this.articleMapper.toEntity(payload));
        return this.articleMapper.toResponse(article);
    }

    @Override
    public ArticleResponse update(Long id, ArticleRequest payload) {
        Article article = articleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        article.setTitle(payload.title());
        article.setContent(payload.content());
        return this.articleMapper.toResponse(this.articleRepository.save(article));
    }

    @Override
    public void delete(Long id) {
        Article article = articleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        article.setDeletedAt(LocalDateTime.now());
        articleRepository.save(article);
    }
}
