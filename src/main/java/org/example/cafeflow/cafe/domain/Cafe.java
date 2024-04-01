package org.example.cafeflow.cafe.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.review.domain.Review;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "cafe")
    private List<Review> reviews;

    private int reviewsCount;

    private String description;

    private String region;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
