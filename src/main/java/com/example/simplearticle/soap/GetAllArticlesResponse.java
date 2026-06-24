package com.example.simplearticle.soap;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "GetAllArticlesResponse", namespace = "http://example.com/article")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAllArticlesResponse {
    private List<ArticleResponse> articles = new ArrayList<>();

    public List<ArticleResponse> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleResponse> articles) {
        this.articles = articles;
    }
}
