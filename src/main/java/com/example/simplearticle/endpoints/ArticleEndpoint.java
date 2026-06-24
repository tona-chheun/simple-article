package com.example.simplearticle.endpoints;

import com.example.simplearticle.services.ArticleService;
import com.example.simplearticle.soap.*;
import org.springframework.ws.server.endpoint.annotation.*;

@Endpoint
public class ArticleEndpoint {
    private static final String NAMESPACE_URI =
            "http://example.com/article";

    private final ArticleService articleService;

    public ArticleEndpoint(
            ArticleService articleService) {
        this.articleService = articleService;
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "GetAllArticlesRequest")
    @ResponsePayload
    public GetAllArticlesResponse GetAllArticlesRequest(
            @RequestPayload GetAllArticlesRequest request) {
        return articleService.getAll();
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "GetArticleRequest")
    @ResponsePayload
    public ArticleResponse GetArticleRequest(
            @RequestPayload GetArticleRequest request) {
        return articleService.findById(request.getId());
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "CreateArticleRequest")
    @ResponsePayload
    public ArticleResponse CreateArticleRequest(
            @RequestPayload CreateArticleRequest request) {
        return articleService.create(request);
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "UpdateArticleRequest")
    @ResponsePayload
    public ArticleResponse UpdateArticleRequest(
            @RequestPayload UpdateArticleRequest request) {
        return articleService.update(request);
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "DeleteArticleRequest")
    @ResponsePayload
    public DeleteArticleResponse DeleteArticleRequest(
            @RequestPayload DeleteArticleRequest request) {
        articleService.delete(request.getId());
        DeleteArticleResponse response = new DeleteArticleResponse();
        response.setSuccess(true);
        response.setMessage("Article deleted succeed");
        return response;
    }
}
