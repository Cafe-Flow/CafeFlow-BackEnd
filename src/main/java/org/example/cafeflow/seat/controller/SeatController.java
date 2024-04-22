package org.example.cafeflow.seat.controller;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.seat.domain.SeatStatus;
import org.example.cafeflow.seat.dto.RequestSeatDto;
import org.example.cafeflow.seat.dto.ResponseSeatDto;
import org.example.cafeflow.seat.dto.SeatStatusDto;
import org.example.cafeflow.seat.service.SeatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    //WebSocket
    @MessageMapping("/seat") //  -> /app/seat
    @SendTo("/topic/greetings")
    public SeatStatusDto seatStatus(@RequestBody SeatStatus status) {
        SeatStatusDto statusDto = new SeatStatusDto(status);
        return statusDto;
    }

    //Http
    //좌석 등록
    @PostMapping("/api/cafe/{cafe_id}/seat-register")
    public List<Long> registerSeats(@PathVariable("cafe_id") Long cafeId, @RequestBody List<RequestSeatDto> seatDtoList) {
        return seatService.registerSeats(cafeId, seatDtoList);
    }

    //좌석 확인(관리자용)
    @GetMapping("/api/cafe/{cafe_id}/seat")
    public List<ResponseSeatDto> cafeSeats(@PathVariable("cafe_id") Long cafeId) {
        return seatService.cafeSeats(cafeId);
    }

    //좌석 수정(관리자용)
    @PutMapping("/api/cafe/{cafe_id}/seat")
    public List<ResponseSeatDto> updateCafeSeats(@PathVariable("cafe_id") Long cafeId, @RequestBody List<RequestSeatDto> seatDtoList) {
        return seatService.cafeSeats(cafeId);
    }



}
