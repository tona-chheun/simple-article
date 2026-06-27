package com.example.notificationservice.services;

import com.example.notificationservice.config.NotificationRabbitConfig;
import com.example.notificationservice.events.ArticleCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleCreatedConsumerImpl implements ArticleCreatedConsumer {
    private final NotificationService notificationService;

    @RabbitListener(queues = NotificationRabbitConfig.ARTICLE_CREATED_QUEUE)
    public void consume(ArticleCreatedEvent event) {
        notificationService.createArticleCreatedNotification(event);
    }
}
