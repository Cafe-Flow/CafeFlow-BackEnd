package org.example.cafeflow.seat.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.example.cafeflow.seat.domain.Seat;
import org.example.cafeflow.seat.dto.RequestSeatDto;
import org.example.cafeflow.seat.dto.ResponseSeatDto;
import org.example.cafeflow.seat.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatService {

    private final SeatRepository seatRepository;
    private final CafeRepository cafeRepository;

    public List<Long> registerSeats(Long cafeId, List<RequestSeatDto> seatDtoList) {
        Cafe cafe = cafeRepository.findById(cafeId);
        List<Seat> seats = seatDtoList.stream()
                .map(RequestSeatDto::toEntity)
                .collect(Collectors.toList());
        List<Long> id = new ArrayList<>();
        for (Seat seat : seats) {
            seatRepository.save(seat);
            seat.registerSeatToCafe(cafe);
            id.add(seat.getId());
        }
        return id;
    }
    public List<ResponseSeatDto> cafeSeats(Long cafeId) {
        List<Seat> seats = seatRepository.findAll(cafeId);
        return seats.stream()
            .map(s -> ResponseSeatDto.builder()
                    .id(s.getId())
                    .seatHasPlug(s.getSeatHasPlug())
                    .coordinates(s.getCoordinates())
                    .build()
            )
            .collect(Collectors.toList());
    }

    public void updateCafeSeats(Long cafeId, List<RequestSeatDto> seatDtoList) {
        seatRepository.removeAll(cafeId);
        registerSeats(cafeId, seatDtoList);
    }
}
