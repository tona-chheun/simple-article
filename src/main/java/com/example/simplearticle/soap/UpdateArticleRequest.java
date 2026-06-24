package com.example.simplearticle.soap;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "UpdateArticleRequest", namespace = "http://example.com/article")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateArticleRequest {
    private Long id;
    private String title;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
