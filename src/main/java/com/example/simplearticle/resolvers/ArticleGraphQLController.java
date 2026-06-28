package com.example.simplearticle.resolvers;

import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.response.ApiResponse;
import com.example.simplearticle.response.ArticlePageResponse;
import com.example.simplearticle.response.ArticleResponse;
import com.example.simplearticle.services.ArticleService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ArticleGraphQLController {
    private final ArticleService articleService;

    public ArticleGraphQLController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @QueryMapping
    public ArticlePageResponse articles(
            @Argument int page,
            @Argument int size
    ) {
        return articleService.getAll(page, size);
    }

    @QueryMapping
    public ApiResponse<ArticleResponse> article(@Argument Long id) {
        return articleService.findById(id);
    }

    @MutationMapping
    public ArticleResponse createArticle(@Argument ArticleRequest input) {
        return articleService.create(input);
    }

    @MutationMapping
    public ArticleResponse updateArticle(
            @Argument Long id,
            @Argument ArticleRequest input
    ) {
        return articleService.update(id, input);
    }

    @MutationMapping
    public Boolean deleteArticle(@Argument Long id) {
        articleService.delete(id);
        return true;
    }
}
