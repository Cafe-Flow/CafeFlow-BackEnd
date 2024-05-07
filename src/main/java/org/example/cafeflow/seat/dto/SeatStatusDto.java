package org.example.cafeflow.seat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cafeflow.seat.domain.SeatStatus;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SeatStatusDto { //websocket 용
    private SeatStatus seatStatus;
    private int seatNumber;
}
