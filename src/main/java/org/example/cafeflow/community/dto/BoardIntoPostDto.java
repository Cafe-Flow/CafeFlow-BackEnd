package org.example.cafeflow.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardIntoPostDto {
    private Long id;
    private Long boardId;
    private String title;
    private String content;
    private String authorNickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long stateId;
    private String stateName;
    private int likesCount;
    private int views;
    private int commentCount;
}
