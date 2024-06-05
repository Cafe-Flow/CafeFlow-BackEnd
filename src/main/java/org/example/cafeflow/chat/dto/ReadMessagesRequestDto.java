package org.example.cafeflow.chat.dto;

import lombok.Data;
import java.util.List;

@Data
public class ReadMessagesRequestDto {
    private List<Long> messageIds;
}
