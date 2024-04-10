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
    private Long reviewsRating;
    private String description;
    private String region;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
