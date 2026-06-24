package com.example.simplearticle.soap;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "DeleteArticleResponse", namespace = "http://example.com/article")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteArticleResponse {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
