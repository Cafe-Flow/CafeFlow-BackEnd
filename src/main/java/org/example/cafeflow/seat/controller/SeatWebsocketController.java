package org.example.cafeflow.seat.controller;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.seat.domain.SeatStatus;
import org.example.cafeflow.seat.dto.SeatStatusDto;
import org.example.cafeflow.seat.service.SeatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.example.cafeflow.seat.domain.SeatStatus.AVAILABLE;
import static org.example.cafeflow.seat.domain.SeatStatus.RESERVED;

@Controller
@RequiredArgsConstructor
public class SeatWebsocketController {
    private final SeatService seatService;
    //WebSocket
    @MessageMapping("/cafe/{cafeId}/seat") //  -> /app/cafe/1/seat
    @SendTo("/topic/cafe/{cafeId}/seat")
    public SeatStatusDto seatStatus(@DestinationVariable("cafeId") Long cafeId, SeatStatusDto seatStatusDto) {
        seatService.setTimeSeatStatus(cafeId, seatStatusDto);
        return seatStatusDto;
    }
}