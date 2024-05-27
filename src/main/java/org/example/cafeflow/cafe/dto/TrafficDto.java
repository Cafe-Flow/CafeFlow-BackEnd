package org.example.cafeflow.cafe.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cafeflow.cafe.domain.Traffic;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TrafficDto {
    private Traffic traffic;
    private Long cafeId;
}
