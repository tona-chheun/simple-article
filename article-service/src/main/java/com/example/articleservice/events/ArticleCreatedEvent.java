package com.example.articleservice.events;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleCreatedEvent {
    private Long id;
    private String title;
    private String content;

    public ArticleCreatedEvent() {}

    public ArticleCreatedEvent(
            Long id,
            String title,
            String content
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleCreatedEvent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
