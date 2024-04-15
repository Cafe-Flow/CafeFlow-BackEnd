package org.example.cafeflow.community.controller;

import org.example.cafeflow.community.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/send")
    @SendTo("/topic/messages")
    public MessageDto sendMessage(MessageDto message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
        return message;
    }

    @MessageMapping("/chat/private")
    public void sendPrivateMessage(MessageDto message) {
        messagingTemplate.convertAndSendToUser(message.getTo(), "/queue/reply", message);
    }
}
