package org.example.cafeflow.community.dto;

import lombok.Data;

@Data
public class CommentCreationDto {
    private Long postId;
    private Long authorId;
    private String content;
}