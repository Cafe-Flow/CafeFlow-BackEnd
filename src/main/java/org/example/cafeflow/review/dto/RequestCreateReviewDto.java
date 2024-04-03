package org.example.cafeflow.review.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class RequestCreateReviewDto {
    private Long rating;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
