package org.example.cafeflow.seat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    @Builder
    public Seat(Long id, Cafe cafe, SeatStatus seatStatus, SeatHasPlug seatHasPlug, SeatCoordinates coordinates) {
        this.id = id;
        this.cafe = cafe;
        this.seatStatus = seatStatus;
        this.seatHasPlug = seatHasPlug;
        this.coordinates = coordinates;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @Enumerated(EnumType.STRING)
    private SeatHasPlug seatHasPlug;

    @Embedded
    private SeatCoordinates coordinates;
}
