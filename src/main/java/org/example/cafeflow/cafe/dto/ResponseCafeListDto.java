package org.example.cafeflow.cafe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseCafeListDto {
    private String name;
    private int reviewsCount;
}
