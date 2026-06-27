package com.example.articleservice.services;

import com.example.articleservice.config.ArticleRabbitConfig;
import com.example.articleservice.events.ArticleCreatedEvent;
import com.example.articleservice.models.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleEventPublisherImpl implements ArticleEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishArticleCreated(Article article) {
        ArticleCreatedEvent event = new ArticleCreatedEvent(
                article.getId(),
                article.getTitle(),
                article.getContent()
        );

        rabbitTemplate.convertAndSend(
                ArticleRabbitConfig.ARTICLE_EXCHANGE,
                ArticleRabbitConfig.ARTICLE_CREATED_ROUTING_KEY,
                event
        );
        log.info("notify article to notification service");
    }
}
