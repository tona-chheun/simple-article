package com.example.notificationservice.services;

import com.example.notificationservice.events.ArticleCreatedEvent;

public interface ArticleCreatedConsumer {
    void consume(ArticleCreatedEvent event);
}
