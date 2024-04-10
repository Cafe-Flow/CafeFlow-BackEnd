package org.example.cafeflow.cafe.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.review.domain.Review;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //엔티티는 기본 생성자가 있어야 하는데 @AllArgsConstructor때문에 기본이 사라져서 추가
public class Cafe {
    @Builder
    public Cafe(Long id, String name, String address, Member member, int reviewsCount, String description, String region, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.member = member;
        this.reviewsCount = reviewsCount;
        this.description = description;
        this.region = region;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_id")
    private Long id;

    private String name;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private Long reviewsRating = 0L;

    private int reviewsCount;

    private String description;

    private String region;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //카페 정보 수정
    public void updateCafe(String name, String address, String description, String region, LocalDateTime updatedAt) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.region = region;
        this.updatedAt = updatedAt;
    }

    public void upReviewCount() {
        this.reviewsCount++;
    }

    public void averageReviewRating(Long reviewsRating) {
        this.reviewsRating = reviewsRating;
    }
}
