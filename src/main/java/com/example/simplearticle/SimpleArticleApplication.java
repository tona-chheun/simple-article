package com.example.simplearticle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SimpleArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleArticleApplication.class, args);
    }

}
