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

    private static final Map<Long, Set<Integer>> CAFE_SEATS = Map.of(
            21L, Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            22L, Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9),
            23L, Set.of(1, 2, 3, 4, 5, 6, 7)
    );

    private final Random random = new Random();

    @Scheduled(fixedRate = 5000) // 5초마다 실행
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
        stompSession = stompClient.connect("ws://localhost:8080/ws", new StompSessionHandlerAdapter() {}).get();
    }
}
