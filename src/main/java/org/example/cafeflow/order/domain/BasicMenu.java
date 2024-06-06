package org.example.cafeflow.order.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // Coffee, Decaf, Latte, Tea, Smoothie, Juice, Ade, Cake, Dessert, Beverage 등

    @Lob
    @Column(name = "image", columnDefinition="LONGBLOB")
    private byte[] image;
}
