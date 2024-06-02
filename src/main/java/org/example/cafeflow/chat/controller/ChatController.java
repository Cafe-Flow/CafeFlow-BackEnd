package org.example.cafeflow.chat.controller;

import org.example.cafeflow.chat.dto.ChatMessageDto;
import org.example.cafeflow.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {
    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/send")
    @SendTo("/topic/messages")
    public ChatMessageDto send(ChatMessageDto chatMessageDto) {
        chatService.saveMessage(chatMessageDto);
        return chatMessageDto;
    }

    @GetMapping("/chat/history/{roomId}")
    public List<ChatMessageDto> getChatHistory(@PathVariable("roomId") Long roomId) {
        return chatService.getChatHistory(roomId);
    }

    @PostMapping("/chat/read/{messageId}")
    public void markMessageAsRead(@PathVariable("messageId") Long messageId) {
        chatService.updateReadStatus(messageId);
    }
}
