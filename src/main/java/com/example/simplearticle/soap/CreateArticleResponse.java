package com.example.simplearticle.soap;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "CreateArticleResponse", namespace = "http://example.com/article")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateArticleResponse {
    private ArticleResponse article;

    public ArticleResponse getArticle() {
        return article;
    }

    public void setArticle(ArticleResponse article) {
        this.article = article;
    }
}
