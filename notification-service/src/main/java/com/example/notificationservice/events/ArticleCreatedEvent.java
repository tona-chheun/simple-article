package com.example.notificationservice.events;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleCreatedEvent {
    private Long id;
    private String title;
    private String content;

    @Override
    public String toString() {
        return "ArticleCreatedEvent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
