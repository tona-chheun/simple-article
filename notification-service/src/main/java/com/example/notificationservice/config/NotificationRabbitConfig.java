package com.example.notificationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationRabbitConfig {
    public static final String ARTICLE_EXCHANGE = "article.events";
    public static final String ARTICLE_CREATED_QUEUE = "notification.article.created.queue";
    public static final String ARTICLE_CREATED_ROUTING_KEY = "article.created";

    @Bean
    public TopicExchange articleExchange() {
        return new TopicExchange(ARTICLE_EXCHANGE);
    }

    @Bean
    public Queue articleCreatedQueue() {
        return QueueBuilder
                .durable(ARTICLE_CREATED_QUEUE)
                .build();
    }

    @Bean
    public Binding articleCreatedBinding() {
        return BindingBuilder
                .bind(articleCreatedQueue())
                .to(articleExchange())
                .with(ARTICLE_CREATED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
