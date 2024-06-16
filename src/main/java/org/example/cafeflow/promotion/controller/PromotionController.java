package org.example.cafeflow.promotion.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.Member.util.JwtTokenProvider;
import org.example.cafeflow.Member.util.UserPrincipal;
import org.example.cafeflow.promotion.dto.RequestPromotionDto;
import org.example.cafeflow.promotion.dto.ResponsePromotionDto;
import org.example.cafeflow.promotion.service.PromotionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
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

    //프로모션 등록
    @PostMapping("/api/cafe/promotion")
    public ResponsePromotionDto registPromotion(HttpServletRequest request, @ModelAttribute RequestPromotionDto promotionDto) {
        UserPrincipal currentUser = getCurrentUser(request);
        return promotionService.registPromotion(promotionDto, currentUser.getId());
    }

    //프로모션 전체 목록 확인
    @GetMapping("/api/cafe/promotion")
    public List<ResponsePromotionDto> promotionList(@RequestParam("sort-by") String sortBy) {
        return promotionService.findAllBySort(sortBy);
    }

    //특정 카페 프로모션 확인
    @GetMapping("/api/cafe/{id}/promotion")
    public List<ResponsePromotionDto> promotionListByCafe(@PathVariable("id") Long cafeId, @RequestParam("sort-by") String sortBy) {
        return promotionService.findAllByCafeSort(sortBy, cafeId);
    }

    //프로모션 수정
    @PutMapping("/api/cafe/promotion/{promotion_id}")
    public ResponsePromotionDto updatePromotion(HttpServletRequest request, @ModelAttribute RequestPromotionDto promotionDto, @PathVariable("promotion_id") Long pId) {
        UserPrincipal currentUser = getCurrentUser(request);
        return promotionService.updatePromotion(promotionDto, currentUser.getId(), pId);
    }

    //프로모션 수정
    @DeleteMapping("/api/cafe/promotion/{promotion_id}")
    public void deletePromotion(HttpServletRequest request, @PathVariable("promotion_id") Long pId) {
        UserPrincipal currentUser = getCurrentUser(request);
        promotionService.deletePromotion(currentUser.getId(), pId);
    }


}
