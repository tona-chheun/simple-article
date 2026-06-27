package com.example.simplearticle.resolvers;

import com.example.simplearticle.models.Article;
import com.example.simplearticle.response.ArticleResponse;
import com.example.simplearticle.services.ArticleService;
import org.springframework.graphql.data.method.annotation.Argument;
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
    public List<ArticleResponse> articles() {
        return articleService.getAll();
    }

    @QueryMapping
    public ArticleResponse article(@Argument Long id) {
        return articleService.findById(id);
    }
}
