package org.example.cafeflow.cafe.domain;

import jakarta.persistence.*;
import org.example.cafeflow.Member.domain.Member;

import java.time.LocalDateTime;

@Entity
public class Cafe {
    @Id
    @GeneratedValue
    @Column(name = "cafe_id")
    private Long id;

    private String name;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String description;

    private String region;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
