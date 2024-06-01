package org.example.cafeflow.review.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.Member.util.JwtTokenProvider;
import org.example.cafeflow.Member.util.UserPrincipal;
import org.example.cafeflow.review.dto.RequestCreateReviewDto;
import org.example.cafeflow.review.dto.ResponseReviewDto;
import org.example.cafeflow.review.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    //JWT
    private UserPrincipal getCurrentUser(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsername(token);
            Member member = memberRepository.findByLoginId(username)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            return new UserPrincipal(member);
        }
        return null;
    }
    //카페 리뷰 등록
    @PostMapping("/api/cafe/{cafe_id}/review")
    public Long postReview(HttpServletRequest request, @PathVariable("cafe_id") Long cafeId, @Valid @ModelAttribute RequestCreateReviewDto reviewDto) {
        UserPrincipal currentUser = getCurrentUser(request);
        Long reviewId = reviewService.createReview(cafeId, reviewDto, currentUser.getId());
        return reviewId;
    }

    //카페 리뷰 확인
    @GetMapping("/api/cafe/{cafe_id}/review")
    public List<ResponseReviewDto> cafeReview(@PathVariable("cafe_id") Long cafeId, @RequestParam("sort-by") String sortBy) {
        return reviewService.findAllBySort(cafeId, sortBy);
    }

//    @PostMapping("/cafe/{cafe_id}/review/{review_id}")
//    public void updateReview(@PathVariable("review_id") Long reviewId, @RequestBody RequestCreateReviewDto reviewDto) {
//        reviewService.updateReview(reviewId, reviewDto);
//    }


}
