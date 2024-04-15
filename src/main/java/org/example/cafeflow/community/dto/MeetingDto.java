package org.example.cafeflow.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingDto {
    private Long boardId;
    private String topic;
    private LocalDateTime time;
}
