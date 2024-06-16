package org.example.cafeflow.cafe.dto;

import lombok.*;
import org.example.cafeflow.cafe.domain.Traffic;
import org.example.cafeflow.promotion.dto.ResponsePromotionDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResponseCafeDto {

    private Long id;
    private Long memberId;
    private String name;
    private String address;
    private int reviewCount;
    private double reviewsRating;
    private String description;
    private Integer mapx;
    private Integer mapy;
    private byte[] image;
    private Integer watingTime;
    private Traffic traffic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ResponsePromotionDto> promotions;
}
