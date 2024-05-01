package org.example.cafeflow.seat.dto;

import lombok.*;
import org.example.cafeflow.seat.domain.SeatAngle;
import org.example.cafeflow.seat.domain.SeatCoordinates;
import org.example.cafeflow.seat.domain.SeatStatus;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseSeatDto {

    @Builder
    public ResponseSeatDto(Long id, Boolean seatHasPlug, int seatSize, int seatNumber, SeatAngle seatAngle, SeatCoordinates seatCoordinates) {
        this.id = id;
        this.seatHasPlug = seatHasPlug;
        this.seatSize = seatSize;
        this.seatNumber = seatNumber;
        this.seatAngle = seatAngle;
        this.seatCoordinates = seatCoordinates;
    }

    private Long id;
    private Boolean seatHasPlug;
    private int seatSize;
    private int seatNumber;
    private SeatAngle seatAngle;
    private SeatCoordinates seatCoordinates;
}
