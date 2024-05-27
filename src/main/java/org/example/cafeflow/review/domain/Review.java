package org.example.cafeflow.review.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.cafe.domain.Cafe;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review {

    @Builder
    public Review(Long id, Cafe cafe, Member member, Long rating, String comment, byte[] image, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.cafe = cafe;
        this.member = member;
        this.rating = rating;
        this.comment = comment;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long rating;

    private String comment;

    @Lob
    @Column(name = "image", columnDefinition="LONGBLOB",nullable = true)
    private byte[] image;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



    public void registerdReviewToCafe(Cafe cafe) {
        this.cafe = cafe;
        cafe.addReview(this);
    }
}
