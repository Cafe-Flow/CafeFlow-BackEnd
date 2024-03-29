package org.example.cafeflow.seat.domain;

import jakarta.persistence.*;
import org.example.cafeflow.cafe.domain.Cafe;

@Entity
public class Seat {

    @Id
    @GeneratedValue
    @Column(name = "seat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;
}
