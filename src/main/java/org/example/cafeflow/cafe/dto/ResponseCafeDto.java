package org.example.cafeflow.cafe.dto;

import lombok.*;
import org.example.cafeflow.cafe.domain.CafeCoordinates;

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
    private CafeCoordinates cafeCoordinates;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
