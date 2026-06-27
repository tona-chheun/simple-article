package com.example.notificationservice.services;

import com.example.notificationservice.events.ArticleCreatedEvent;

public interface NotificationService {
    void createArticleCreatedNotification(ArticleCreatedEvent event);
}
