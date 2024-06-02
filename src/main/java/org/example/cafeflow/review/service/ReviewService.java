package org.example.cafeflow.review.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.example.cafeflow.review.domain.Review;
import org.example.cafeflow.review.dto.RequestCreateReviewDto;
import org.example.cafeflow.review.dto.ResponseReviewDto;
import org.example.cafeflow.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;

    public Long createReview(Long cafeId, RequestCreateReviewDto reviewDto, Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Review review = reviewDto.toEntity(reviewDto, member.getNickname()); //리뷰 생성

        Cafe cafe = cafeRepository.findById(cafeId); //생성한 리뷰 카페랑 묶기
        cafe.upReviewCount();//리뷰 수 증가

        Long totalRating = 0L; // 총 평점
        review.registerdReviewToCafe(cafe);
        review.registerReviewToMember(member);
        reviewRepository.save(review);

        List<Review> reviews = reviewRepository.findByCafeId(cafeId);

        for (Review r : reviews) {
            totalRating += r.getRating();
        }

        double averageRating = (double) totalRating / reviews.size(); // 평균 평점을 계산합니다.
        double roundedAverageRating = Math.round(averageRating * 10.0) / 10.0; // 한 자리 소수점으로 반올림합니다.
        cafe.averageReviewRating(roundedAverageRating); // 카페의 평균 평점을 설정합니다.

        return review.getId();

    }

    public List<ResponseReviewDto> findAllBySort(Long cafeId, String sortBy) {
        List<Review> reviews;
        switch (sortBy) {
            case "highest-rating":
                reviews = reviewRepository.findAllByHighestRating(cafeId, sortBy);
                break;
            case "lowest-rating":
                reviews = reviewRepository.findAllByLowestRating(cafeId, sortBy);
                break;
            case "latest":
                reviews = reviewRepository.findAllByLatest(cafeId, sortBy);
                break;
            default:
                reviews = reviewRepository.findByCafeId(cafeId);
                break;
        }
        return reviews.stream()
                .map(r -> ResponseReviewDto.builder()
                        .id(r.getId())
                        .nickname(r.getNickname())
                        .rating(r.getRating())
                        .comment(r.getComment())
                        .image(r.getImage())
                        .createdAt(r.getCreatedAt())
                        .updatedAt(r.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }


//    public void updateReview(Long reviewId, RequestCreateReviewDto reviewDto) {
//        Review review = reviewRepository.findById(reviewId)
//    }
}
