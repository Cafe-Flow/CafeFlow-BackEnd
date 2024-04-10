package org.example.cafeflow.review.controller;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.review.dto.RequestCreateReviewDto;
import org.example.cafeflow.review.dto.ResponseReviewDto;
import org.example.cafeflow.review.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //카페 리뷰 등록
    @PostMapping("/api/cafe/{cafe_id}/review")
    public Long postReview(@PathVariable("cafe_id") Long cafeId, @RequestBody RequestCreateReviewDto reviewDto) {
        Long reviewId = reviewService.createReview(cafeId, reviewDto);
        return reviewId;
    }

    //카페 리뷰 확인
    @GetMapping("/api/cafe/{cafe_id}/review")
    public List<ResponseReviewDto> cafeReview(@PathVariable("cafe_id") Long cafeId) {
        return reviewService.findByCafeId(cafeId);
    }

//    @PostMapping("/cafe/{cafe_id}/review/{review_id}")
//    public void updateReview(@PathVariable("review_id") Long reviewId, @RequestBody RequestCreateReviewDto reviewDto) {
//        reviewService.updateReview(reviewId, reviewDto);
//    }


}
