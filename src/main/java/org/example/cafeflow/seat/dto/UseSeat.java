package org.example.cafeflow.seat.dto;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UseSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "use_seat_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column(name = "use_at")
    private String useAt;

    private int seatNumber;

    @Builder
    public UseSeat(Long id, Cafe cafe, String useAt, int seatNumber) {
        this.id = id;
        this.cafe = cafe;
        this.useAt = useAt;
        this.seatNumber = seatNumber;
    }
}
