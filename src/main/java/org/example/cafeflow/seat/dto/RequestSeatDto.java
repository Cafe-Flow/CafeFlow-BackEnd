package org.example.cafeflow.seat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cafeflow.seat.domain.Seat;
import org.example.cafeflow.seat.domain.SeatCoordinates;
import org.example.cafeflow.seat.domain.SeatStatus;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RequestSeatDto {
    private Boolean seatHasPlug;
    private SeatCoordinates coordinates;

    public Seat toEntity() {
        Seat seat = Seat.builder()
                .seatHasPlug(seatHasPlug)
                .coordinates(coordinates)
                .build();
        return seat;
    }
}
