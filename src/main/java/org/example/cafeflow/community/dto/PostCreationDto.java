package org.example.cafeflow.community.dto;

import lombok.Data;

@Data
public class PostCreationDto {
    private Long authorId;
    private Long boardId;
    private String content;
}