package org.example.cafeflow.cafe.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResponseCafeDto {

    private Long id;
    private String name;
    private String address;
    private int reviewCount;
    private double reviewsRating;
    private String description;
    private int mapx;
    private int mapy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
