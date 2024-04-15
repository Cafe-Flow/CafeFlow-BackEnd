package org.example.cafeflow.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreationDto {
    private Long authorId;
    private Long boardId;
    private String content;
    private String imageUrl;
}
