package org.example.cafeflow.seat.simulation;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.seat.domain.SeatStatus;
import org.example.cafeflow.seat.dto.SeatStatusDto;
import org.example.cafeflow.seat.service.SeatService;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class SeatStatusSimulation {

    private final WebSocketStompClient stompClient;
    private StompSession stompSession;
    private final SeatService seatService; // SeatService를 주입받음

    private static final Map<Long, Set<Integer>> CAFE_SEATS;

    static {
        Map<Long, Set<Integer>> cafeSeats = new HashMap<>();
        cafeSeats.put(2L, Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        cafeSeats.put(3L, Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        cafeSeats.put(5L, Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        cafeSeats.put(6L, Set.of(1, 2, 3, 4, 5, 6, 7, 8));
        cafeSeats.put(7L, Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        cafeSeats.put(8L, Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13));
        cafeSeats.put(9L, Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        cafeSeats.put(10L, Set.of(1, 2, 3, 4, 5));
        cafeSeats.put(11L, Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        cafeSeats.put(12L, Set.of(1, 2));
        cafeSeats.put(13L, Set.of(1, 2, 3, 4, 5, 6));
        cafeSeats.put(14L, Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19));
        cafeSeats.put(15L, Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        cafeSeats.put(16L, Set.of(1, 2, 3));
        cafeSeats.put(17L, Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16));
        cafeSeats.put(18L, Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        CAFE_SEATS = Map.copyOf(cafeSeats);
    }

    private final Random random = new Random();

    @Scheduled(fixedRate = 5000)
    public void simulateSeatStatus() throws ExecutionException, InterruptedException {
        if (stompSession == null || !stompSession.isConnected()) {
            connectWebSocket();
        }

        for (Map.Entry<Long, Set<Integer>> entry : CAFE_SEATS.entrySet()) {
            Long cafeId = entry.getKey();
            Integer[] seats = entry.getValue().toArray(new Integer[0]);
            int seatNumber = seats[random.nextInt(seats.length)];

            // 현재 좌석 상태를 확인
            SeatStatus currentStatus = seatService.getSeatStatus(cafeId, seatNumber);
            SeatStatus newStatus = currentStatus == SeatStatus.AVAILABLE ? SeatStatus.OCCUPIED : SeatStatus.AVAILABLE;

            SeatStatusDto seatStatusDto = new SeatStatusDto(newStatus, seatNumber);
            stompSession.send("/app/cafe/" + cafeId + "/seat", seatStatusDto);
            System.out.println("Sent to cafe " + cafeId + " seat " + seatNumber + ": " + newStatus);
        }
    }

    private void connectWebSocket() throws ExecutionException, InterruptedException {
        SockJsClient sockJsClient = new SockJsClient(List.of(new WebSocketTransport(new StandardWebSocketClient())));
        stompClient.setMessageConverter(new org.springframework.messaging.converter.MappingJackson2MessageConverter());
        stompSession = stompClient.connect("ws://cafeflow.store:8080/ws", new StompSessionHandlerAdapter() {}).get();
    }
}
