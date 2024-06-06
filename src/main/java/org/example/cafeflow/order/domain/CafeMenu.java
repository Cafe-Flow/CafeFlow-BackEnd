package org.example.cafeflow.order.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.cafeflow.cafe.domain.Cafe;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CafeMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basic_menu_id", nullable = false)
    private BasicMenu basicMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe;

    @Column(nullable = false)
    private Double price;
}
