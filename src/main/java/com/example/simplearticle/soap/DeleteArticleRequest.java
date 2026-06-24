package com.example.simplearticle.soap;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "DeleteArticleRequest", namespace = "http://example.com/article")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteArticleRequest {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
