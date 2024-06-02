package org.example.cafeflow.seat.controller;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.seat.domain.SeatStatus;
import org.example.cafeflow.seat.dto.RequestSeatDto;
import org.example.cafeflow.seat.dto.ResponseSeatDto;
import org.example.cafeflow.seat.dto.SeatStatusDto;
import org.example.cafeflow.seat.service.SeatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

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

//    좌석 수정(관리자용)
    @PutMapping("/api/cafe/{cafe_id}/seat")
    public List<RequestSeatDto> updateCafeSeats(@PathVariable("cafe_id") Long cafeId, @RequestBody List<RequestSeatDto> seatDtoList) {
        seatService.updateCafeSeats(cafeId, seatDtoList);
        return seatDtoList;
    }

    //좌석 확인(사용자용)
    @GetMapping("/api/cafe/{cafe_id}/real-time-seat")
    public List<ResponseSeatDto> realTimeCafeSeats(@PathVariable("cafe_id") Long cafeId) {
        return seatService.cafeSeats(cafeId);
    }
}
