package org.example.cafeflow.review.service;

import lombok.RequiredArgsConstructor;
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

    public Long createReview(Long cafeId, RequestCreateReviewDto reviewDto) {
        Review review = reviewDto.toEntity(reviewDto); //리뷰 생성

        Cafe cafe = cafeRepository.findById(cafeId); //생성한 리뷰 카페랑 묶기
        cafe.upReviewCount();//리뷰 수 증가

        Long totalRating = 0L; // 총 평점
        review.registerdReviewToCafe(cafe);
        reviewRepository.save(review);

        List<Review> reviews = reviewRepository.findByCafeId(cafeId);

        for (Review r : reviews) {
            totalRating += r.getRating();
        }

        double averageRating = (double) totalRating / reviews.size(); // 평균 평점을 계산합니다.
        double roundedAverageRating = Math.round(averageRating * 10.0) / 10.0; // 한 자리 소수점으로 반올림합니다.

        // Long으로 형 변환하여 전달합니다.
        Long roundedAverageRatingLong = Math.round(roundedAverageRating);

        cafe.averageReviewRating(roundedAverageRatingLong); // 카페의 평균 평점을 설정합니다.
        Long a = 0L;
        double rating = 7;

        System.out.println();
        return review.getId();

    }

    public List<ResponseReviewDto> findByCafeId(Long cafeId) {
        List<Review> reviews = reviewRepository.findByCafeId(cafeId);
        return reviews.stream()
                .map(r -> ResponseReviewDto.builder()
                        .id(r.getId())
                        .rating(r.getRating())
                        .comment(r.getComment())
                        .createdAt(r.getCreatedAt())
                        .updatedAt(r.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }


//    public void updateReview(Long reviewId, RequestCreateReviewDto reviewDto) {
//        Review review = reviewRepository.findById(reviewId)
//    }
}
