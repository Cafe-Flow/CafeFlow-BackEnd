package org.example.cafeflow.seat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.seat.domain.Seat;
import org.example.cafeflow.seat.domain.SeatAngle;
import org.example.cafeflow.seat.domain.SeatCoordinates;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RequestSeatDto {
    private Boolean seatHasPlug;
    private int seatSize;
    private int seatNumber;
    private SeatAngle seatAngle;
    private SeatCoordinates seatCoordinates;

    public Seat toEntity() {
        Seat seat = Seat.builder()
                .seatHasPlug(seatHasPlug)
                .seatSize(seatSize)
                .seatNumber(seatNumber)
                .seatAngle(seatAngle)
                .seatCoordinates(seatCoordinates)
                .build();
        return seat;
    }

}
