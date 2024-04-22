package org.example.cafeflow.seat.dto;

import lombok.*;
import org.example.cafeflow.seat.domain.SeatCoordinates;
import org.example.cafeflow.seat.domain.SeatStatus;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseSeatDto {

    @Builder
    public ResponseSeatDto(Long id, SeatStatus seatStatus, Boolean seatHasPlug, SeatCoordinates coordinates) {
        this.id = id;
        this.seatStatus = seatStatus;
        this.seatHasPlug = seatHasPlug;
        this.coordinates = coordinates;
    }

    private Long id;
    private SeatStatus seatStatus;
    private Boolean seatHasPlug;
    private SeatCoordinates coordinates;
}
