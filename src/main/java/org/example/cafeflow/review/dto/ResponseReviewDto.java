package org.example.cafeflow.review.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseReviewDto {

    @Builder
    public ResponseReviewDto(Long id, Long rating, String comment, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private Long id;
    private Long rating;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
