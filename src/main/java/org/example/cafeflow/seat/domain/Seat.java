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
    public Seat(Long id, Cafe cafe, SeatStatus seatStatus, Boolean seatHasPlug, int seatSize, int seatNumber, SeatAngle seatAngle, SeatCoordinates seatCoordinates) {
        this.id = id;
        this.cafe = cafe;
        this.seatStatus = (seatStatus == null) ? SeatStatus.AVAILABLE : seatStatus;
        this.seatHasPlug = seatHasPlug;
        this.seatSize = seatSize;
        this.seatNumber = seatNumber;
        this.seatAngle = seatAngle;
        this.seatCoordinates = seatCoordinates;
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

    private Boolean seatHasPlug;
    private int seatSize;
    private int seatNumber;
    @Enumerated(EnumType.STRING)
    private SeatAngle seatAngle;
    @Embedded
    private SeatCoordinates seatCoordinates;


    public void registerSeatToCafe(Cafe cafe) {
        this.cafe = cafe;
        cafe.addSeat(this);
    }

    public void changeSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }
}
