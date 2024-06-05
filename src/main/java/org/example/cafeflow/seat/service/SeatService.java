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


    //webSocket
    public void setTimeSeatStatus(Long cafeId, SeatStatusDto seatStatusDto) {
        Cafe cafe = cafeRepository.findById(cafeId);

        Seat seat = seatRepository.findBySeatNumber(cafeId, seatStatusDto.getSeatNumber());
        System.out.println("seatNumber = " + seat.getSeatNumber());
        seat.changeSeatStatus(seatStatusDto.getSeatStatus());//좌석 상태 변경

        if (seatStatusDto.getSeatStatus() == SeatStatus.AVAILABLE) {
            cafe.upAccumulationUseCount(); //누적 이용 테이블 수 증가(웨이팅 시간 평균 구할 시 사용)
            // 앉아있던 사람이 나왔으니 좌석번호를 이용해 DB 조회 후 시간과 현재시간을 연산하여 앉아있던 시간 계산
            UseSeat useSeat = useSeatRepository.findBySeatNumber(cafeId, seatStatusDto.getSeatNumber());
            String occupied = useSeat.getUseAt();
            String available = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd:HH:mm"));
            int timeDifferenceInMinutes = 0;

            // "일:시:분" 형태의 문자열을 분리
            String[] occupiedParts = occupied.split(":");
            String[] availableParts = available.split(":");

            // 각각의 값에서 시간과 분을 추출하여 계산합니다.
            int occupiedHour = Integer.parseInt(occupiedParts[1]);
            int occupiedMinute = Integer.parseInt(occupiedParts[2]);
            int availableHour = Integer.parseInt(availableParts[1]);
            int availableMinute = Integer.parseInt(availableParts[2]);

            // 날짜가 다르면 24시간을 더하기
            if (!occupiedParts[0].equals(availableParts[0])) {
                timeDifferenceInMinutes += 24 * 60;
            }

            // 시간 차이를 분 단위로 계산
            timeDifferenceInMinutes += (availableHour - occupiedHour) * 60;
            timeDifferenceInMinutes += (availableMinute - occupiedMinute);

            useSeatRepository.delete(useSeat.getId());
            System.out.println("시간 차이(분): " + timeDifferenceInMinutes);
            cafe.updateAccumulationTime(timeDifferenceInMinutes); //누적 카페 이용시간

            Integer watingTime = cafe.getAccumulationTime()/cafe.getAccumulationUse();
            cafe.updateWatingTime(watingTime);

        } else if (seatStatusDto.getSeatStatus() == SeatStatus.RESERVED) {
            System.out.println("예약");
        } else { //OCCUPIED
            //1. 사람이 앉았으니 현재시간과 좌석번호를 DB에 저장 -> 일:시:분
            String occupiedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd:HH:mm"));
            System.out.println("앉은 시간 = " + occupiedTime);
            UseSeat useSeat = UseSeat.builder()
                            .cafe(cafe)
                            .useAt(occupiedTime)
                            .seatNumber(seatStatusDto.getSeatNumber())
                            .build();
            useSeatRepository.save(useSeat);
        }
    }
}
