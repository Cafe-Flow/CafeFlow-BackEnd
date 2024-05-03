package org.example.cafeflow.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private Long boardId;
    private String title;
    private String content;
    private byte[] image;
    private String authorNickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentDto> comments;
    private Long stateId;
    private String stateName;
    private boolean likedByCurrentUser;
    private int likesCount;

    public void updateLikeStatus(boolean likedByCurrentUser, int likesCount) {
        this.likedByCurrentUser = likedByCurrentUser;
        this.likesCount = likesCount;
    }
    private int views;
}
