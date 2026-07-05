package com.example.simplearticle.controllers;

import com.example.simplearticle.requests.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("chat")
public class ChatController {

    @GetMapping
    public String index() {
        return "chat/index";
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        message.setTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        message.setMine(true);
        return message;
    }
}
