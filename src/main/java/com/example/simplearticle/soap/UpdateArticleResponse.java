package com.example.simplearticle.soap;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "UpdateArticleResponse", namespace = "http://example.com/article")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateArticleResponse {
    private ArticleResponse article;

    public ArticleResponse getArticle() {
        return article;
    }

    public void setArticle(ArticleResponse article) {
        this.article = article;
    }
}
