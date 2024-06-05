package org.example.cafeflow.chat.dto;

import lombok.Data;

@Data
public class ChatRoomDto {
    private Long id;
    private Long cafeOwnerId;
    private String cafeOwnerUsername;
    private Long userId;
    private String userUsername;
}
