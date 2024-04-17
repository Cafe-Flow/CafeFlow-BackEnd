package org.example.cafeflow.cafe.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeCoordinates {
    String mapX;
    String mapY;

    public CafeCoordinates(String mapX, String mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
    }
}
