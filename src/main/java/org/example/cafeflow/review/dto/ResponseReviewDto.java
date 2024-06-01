package org.example.cafeflow.review.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseReviewDto {

    @Builder
    public ResponseReviewDto(Long id, String nickname, Long rating, String comment, byte[] image, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nickname = nickname;
        this.rating = rating;
        this.comment = comment;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private Long id;
    private String nickname;
    private Long rating;
    private String comment;
    private byte[] image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
