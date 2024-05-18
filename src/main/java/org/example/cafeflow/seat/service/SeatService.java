package org.example.cafeflow.seat.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.example.cafeflow.seat.domain.Seat;
import org.example.cafeflow.seat.domain.SeatStatus;
import org.example.cafeflow.seat.dto.RequestSeatDto;
import org.example.cafeflow.seat.dto.ResponseSeatDto;
import org.example.cafeflow.seat.dto.SeatStatusDto;
import org.example.cafeflow.seat.dto.UseSeat;
import org.example.cafeflow.seat.repository.SeatRepository;
import org.example.cafeflow.seat.repository.UseSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatService {

    private final SeatRepository seatRepository;
    private final UseSeatRepository useSeatRepository;
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
                    .seatStatus(s.getSeatStatus())
                    .seatSize(s.getSeatSize())
                    .seatNumber(s.getSeatNumber())
                    .seatAngle(s.getSeatAngle())
                    .seatCoordinates(s.getSeatCoordinates())
                    .build()
            )
            .collect(Collectors.toList());
    }

    public void updateCafeSeats(Long cafeId, List<RequestSeatDto> seatDtoList) {
        seatRepository.removeAll(cafeId);
        registerSeats(cafeId, seatDtoList);
    }


    //webSocket
    public void setTimeSeatStatus(Long cafeId, SeatStatusDto seatStatusDto) {
        Cafe cafe = cafeRepository.findById(cafeId);
        if (seatStatusDto.getSeatStatus().equals(SeatStatus.AVAILABLE)) {
            //2. 앉아있던 사람이 나왔으니 좌석번호를 이용해 DB조회 후 시간과 현재시간을 연산하여 앉아있던 시간 계산
            UseSeat useSeat = useSeatRepository.findBySeatNumber(seatStatusDto.getSeatNumber());
            String availableTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd:HH:mm"));

            if (!useSeat.getUseAt().split(":")[0].equals(availableTime.split(":")[0])) { //날짜가 바뀌었다면

            } else{ //날짜가 그대로라면

            }

        } else if (seatStatusDto.getSeatStatus().equals(SeatStatus.RESERVED)) {

        } else { //OCCUPIED
            //1. 사람이 앉았으니 현재시간과 좌석번호를 DB에 저장 -> 일:시:분
            String occupiedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd:HH:mm"));
            UseSeat useSeat = UseSeat.builder()
                            .cafe(cafe)
                            .useAt(occupiedTime)
                            .seatNumber(seatStatusDto.getSeatNumber())
                            .build();
            useSeatRepository.save(useSeat);
        }
    }
}
