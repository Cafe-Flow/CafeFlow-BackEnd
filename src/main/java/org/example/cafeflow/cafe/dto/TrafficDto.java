package org.example.cafeflow.cafe.dto;

import lombok.*;
import org.example.cafeflow.cafe.domain.Traffic;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrafficDto {
    private Long cafeId;
    private Traffic traffic;
    private Integer watingTime;

    @Builder
    public TrafficDto(Long cafeId, Traffic traffic, Integer watingTime) {
        this.cafeId = cafeId;
        this.traffic = traffic;
        this.watingTime = watingTime;
    }
}
