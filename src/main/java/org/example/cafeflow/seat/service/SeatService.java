package org.example.cafeflow.seat.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.example.cafeflow.seat.domain.Seat;
import org.example.cafeflow.seat.domain.SeatStatus;
import org.example.cafeflow.seat.dto.RequestSeatDto;
import org.example.cafeflow.seat.dto.ResponseSeatDto;
import org.example.cafeflow.seat.dto.SeatStatusDto;
import org.example.cafeflow.seat.domain.UseSeat;
import org.example.cafeflow.seat.repository.SeatRepository;
import org.example.cafeflow.seat.repository.UseSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
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

    // 특정 좌석의 상태를 가져오는 메서드 추가
    public SeatStatus getSeatStatus(Long cafeId, int seatNumber) {
        Seat seat = seatRepository.findBySeatNumber(cafeId, seatNumber);
        return seat.getSeatStatus();
    }
    //webSocket
    public void setTimeSeatStatus(Long cafeId, SeatStatusDto seatStatusDto) {
        Cafe cafe = cafeRepository.findById(cafeId);
        Seat seat = seatRepository.findBySeatNumber(cafeId, seatStatusDto.getSeatNumber());
        seat.changeSeatStatus(seatStatusDto.getSeatStatus());

        if (seatStatusDto.getSeatStatus() == SeatStatus.AVAILABLE) {
            cafe.upAccumulationUseCount();

            // 모든 사용 기록을 가져와서 삭제
            List<UseSeat> useSeats = useSeatRepository.findBySeatNumber(cafeId, seatStatusDto.getSeatNumber());
            for (UseSeat useSeat : useSeats) {
                String occupied = useSeat.getUseAt();
                String available = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd:HH:mm"));
                int timeDifferenceInMinutes = calculateTimeDifference(occupied, available);
                useSeatRepository.delete(useSeat.getId());
                cafe.updateAccumulationTime(timeDifferenceInMinutes);
            }

            Integer watingTime = cafe.getAccumulationTime() / cafe.getAccumulationUse();
            cafe.updateWatingTime(watingTime);

        } else if (seatStatusDto.getSeatStatus() == SeatStatus.RESERVED) {
            System.out.println("예약");
        } else { // OCCUPIED
            String occupiedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd:HH:mm"));
            UseSeat useSeat = UseSeat.builder()
                    .cafe(cafe)
                    .useAt(occupiedTime)
                    .seatNumber(seatStatusDto.getSeatNumber())
                    .build();
            useSeatRepository.save(useSeat);
        }
    }

    private int calculateTimeDifference(String occupied, String available) {
        String[] occupiedParts = occupied.split(":");
        String[] availableParts = available.split(":");
        int timeDifferenceInMinutes = 0;

        int occupiedHour = Integer.parseInt(occupiedParts[1]);
        int occupiedMinute = Integer.parseInt(occupiedParts[2]);
        int availableHour = Integer.parseInt(availableParts[1]);
        int availableMinute = Integer.parseInt(availableParts[2]);

        if (!occupiedParts[0].equals(availableParts[0])) {
            timeDifferenceInMinutes += 24 * 60;
        }

        timeDifferenceInMinutes += (availableHour - occupiedHour) * 60;
        timeDifferenceInMinutes += (availableMinute - occupiedMinute);

        return timeDifferenceInMinutes;
    }
}
