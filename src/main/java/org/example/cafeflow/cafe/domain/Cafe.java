package org.example.cafeflow.cafe.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.cafe.dto.TrafficDto;
import org.example.cafeflow.review.domain.Review;
import org.example.cafeflow.seat.domain.Seat;
import org.example.cafeflow.seat.domain.UseSeat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //엔티티는 기본 생성자가 있어야 하는데 @AllArgsConstructor때문에 기본이 사라져서 추가
public class Cafe {

    @Builder
    public Cafe(Long id, String name, String address, Member member, List<Review> reviews, List<Seat> seats, double reviewsRating, int reviewsCount, Integer accumulationUse, Integer accumulationTime, String description, Integer mapx, Integer mapy, byte[] image, Integer watingTime, Traffic traffic, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.member = member;
        this.reviews = reviews;
        this.seats = seats;
        this.reviewsRating = reviewsRating;
        this.reviewsCount = reviewsCount;
        this.accumulationUse = (accumulationUse == null) ? 0 : accumulationUse;
        this.accumulationTime = (accumulationTime == null) ? 0 : accumulationTime;
        this.description = description;
        this.mapx = mapx;
        this.mapy = mapy;
        this.image = image;
        this.watingTime = (watingTime == null) ? 0 : watingTime;
        this.traffic = (traffic == null) ? Traffic.GREEN : traffic;
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

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.REMOVE)
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.REMOVE)
    private List<UseSeat> useSeats = new ArrayList<>();

    @Column(name = "reviews_rating")
    private double reviewsRating;

    private int reviewsCount;

    @Column(name = "accumulation_use")
    private Integer accumulationUse;

    @Column(name = "accumulation_time")
    private Integer accumulationTime;

    private String description;
    private Integer mapx;
    private Integer mapy;

    @Lob
    @Column(name = "image", columnDefinition="LONGBLOB",nullable = true)
    private byte[] image;

    @Column(name = "wating_time")
    private Integer watingTime;

    @Enumerated(EnumType.STRING)
    private Traffic traffic;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    //카페 정보 수정
    public void updateCafe(String name, String address, String description, Integer mapx, Integer mapy, byte[] image, LocalDateTime updatedAt) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.mapx = mapx;
        this.mapy = mapy;
        this.image = image;
        this.updatedAt = updatedAt;
    }

    public void upReviewCount() {
        this.reviewsCount++;
    }

    public void averageReviewRating(double reviewsRating) {
        this.reviewsRating = reviewsRating;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }
    public void registerUser(Member member) {
        this.member = member;
    }

    public void removeAllSeat() {
        seats.clear();
    }

    public void updateTraffic(Traffic traffic) {
        this.traffic = traffic;
    }

    public void upAccumulationUseCount() {
        this.accumulationUse++;
    }

    public void updateWatingTime(Integer watingTime) {
        this.watingTime = watingTime;
    }

    public void updateAccumulationTime(Integer accumulationTime) {
        this.accumulationTime += accumulationTime;
    }
}
