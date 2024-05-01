package org.example.cafeflow.seat.controller;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.seat.domain.SeatStatus;
import org.example.cafeflow.seat.dto.SeatStatusDto;
import org.example.cafeflow.seat.service.SeatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class SeatWebsocketController {
    private final SeatService seatService;
    //WebSocket
    @MessageMapping("/seat") //  -> /app/seat
    @SendTo("/topic/greetings")
    public SeatStatusDto seatStatus(String seatStatus) {
        SeatStatusDto seatStatusDto = new SeatStatusDto(seatStatus.valueOf(seatStatus));
        return seatStatusDto;
    }
}
