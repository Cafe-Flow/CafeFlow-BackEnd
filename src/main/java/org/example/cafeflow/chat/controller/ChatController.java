package org.example.cafeflow.chat.controller;

import org.example.cafeflow.chat.dto.ChatMessageDto;
import org.example.cafeflow.chat.dto.ReadMessagesRequestDto;
import org.example.cafeflow.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/send")
    public void send(ChatMessageDto chatMessageDto) {
        chatService.saveMessage(chatMessageDto);
        messagingTemplate.convertAndSend("/topic/messages/" + chatMessageDto.getChatRoomId(), chatMessageDto);
    }

    @GetMapping("/chat/history/{roomId}")
    public List<ChatMessageDto> getChatHistory(@PathVariable("roomId") Long roomId) {
        return chatService.getChatHistory(roomId);
    }

    @PostMapping("/chat/read")
    public void markMessagesAsRead(@RequestBody ReadMessagesRequestDto request, @RequestParam("userId") Long userId) {
        chatService.updateReadStatus(request.getMessageIds(), userId);
    }

    @GetMapping("/chat/unread-messages/{roomId}")
    public List<ChatMessageDto> getUnreadMessages(@PathVariable("roomId") Long roomId, @RequestParam("userId") Long userId) {
        return chatService.getUnreadMessages(roomId, userId);
    }
}
