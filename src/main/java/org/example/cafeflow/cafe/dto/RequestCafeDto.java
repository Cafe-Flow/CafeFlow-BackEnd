package org.example.cafeflow.cafe.dto;

import lombok.*;
import org.example.cafeflow.cafe.domain.Cafe;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RequestCafeDto {
    private String name;

    private String address;

    private String description;

    private String region;

    public Cafe toEntity() {
        LocalDateTime time = LocalDateTime.now();
        return Cafe.builder()
                .name(name)
                .address(address)
                .reviewsCount(0)
                .description(description)
                .region(region)
                .createdAt(time)
                .updatedAt(time)
                .build();
    }
}
