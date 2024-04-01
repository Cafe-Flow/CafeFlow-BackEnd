package org.example.cafeflow.cafe.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.review.domain.Review;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor // 이건 Bulider패턴 사용때문에 존재
@NoArgsConstructor //엔티티는 기본 생성자가 있어야 하는데 @AllArgsConstructor때문에 기본이 사라져서 추가
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

    private int reviewsCount;

    private String description;

    private String region;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
