package org.example.cafeflow.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private Long chatRoomId;
    private String content;
    private boolean senderReadStatus;
    private boolean receiverReadStatus;
}
