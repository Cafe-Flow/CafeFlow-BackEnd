package org.example.cafeflow.review.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.example.cafeflow.review.domain.Review;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreateReviewDto {

    @Builder
    public RequestCreateReviewDto(Long rating, String comment, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.rating = rating;
        this.comment = comment;
    }
    private Long rating;

    private String comment;

    public Review toEntity(RequestCreateReviewDto reviewDto) {
        LocalDateTime time = LocalDateTime.now();
        return Review.builder()
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .createdAt(time)
                .updatedAt(time)
                .build();
    }
}
