package org.example.cafeflow.review.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.example.cafeflow.review.domain.Review;
import org.example.cafeflow.review.dto.RequestCreateReviewDto;
import org.example.cafeflow.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CafeRepository cafeRepository;

    public Long createReview(RequestCreateReviewDto reviewDto) {
        LocalDateTime time = LocalDateTime.now();
        Review review = Review.builder()
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .createdAt(time)
                .updatedAt(time)
                .build();
        return 1L;
    }
}
