package org.example.cafeflow.seat.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;


@Getter
@Embeddable
public class SeatCoordinates {

    private int x;
    private int y;
}
