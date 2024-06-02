package org.example.cafeflow.review.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.review.domain.Review;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RequestCreateReviewDto {

    @Builder
    public RequestCreateReviewDto(Long rating, String comment, byte[] image, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.rating = rating;
        this.comment = comment;
    }

    @Min(value = 1, message = "별점을 매겨주세요!")
    private Long rating;

    private String comment;

    private MultipartFile image;

    public Review toEntity(RequestCreateReviewDto reviewDto, String nickname) {
        LocalDateTime time = LocalDateTime.now();
        byte[] imageBytes = null;
        try {
            imageBytes = image != null ? image.getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("이미지 변환 중 오류가 발생했습니다.", e);
        }
        return Review.builder()
                .nickname(nickname)
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .image(imageBytes)
                .createdAt(time)
                .updatedAt(time)
                .build();
    }
}
