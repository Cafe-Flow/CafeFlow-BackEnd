package org.example.cafeflow.cafe.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ResponseCafeInfoDto {

    private String name;
    private String address;
    private int reviewCount;
    private String description;
    private String region;
    private LocalDateTime createdAt;
}
