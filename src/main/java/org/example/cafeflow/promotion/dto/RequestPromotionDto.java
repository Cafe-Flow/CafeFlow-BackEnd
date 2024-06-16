package org.example.cafeflow.promotion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.promotion.domain.Promotion;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestPromotionDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private MultipartFile image;
    private Long cafeId;

    public Promotion toEntity(Member member, Cafe cafe) {
        LocalDateTime createdAt = LocalDateTime.now();

        byte[] imageBytes = null;
        try {
            imageBytes = image != null ? image.getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("이미지 변환 중 오류가 발생했습니다.", e);
        }

        return Promotion.builder()
                .member(member)
                .cafe(cafe)
                .createdAt(createdAt)
                .startDate(startDate)
                .endDate(endDate)
                .description(description)
                .image(imageBytes)
                .build();
    }
}
