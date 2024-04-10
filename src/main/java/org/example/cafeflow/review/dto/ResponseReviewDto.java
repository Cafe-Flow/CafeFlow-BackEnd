package org.example.cafeflow.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
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
