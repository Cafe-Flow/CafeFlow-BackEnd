package org.example.cafeflow.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomRequest {
    private Long cafeOwnerId;
    private Long userId;
}
