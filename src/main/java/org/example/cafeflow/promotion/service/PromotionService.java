package org.example.cafeflow.promotion.service;


import lombok.RequiredArgsConstructor;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.example.cafeflow.exception.UnauthorizedAccessException;
import org.example.cafeflow.promotion.domain.Promotion;
import org.example.cafeflow.promotion.dto.RequestPromotionDto;
import org.example.cafeflow.promotion.dto.ResponsePromotionDto;
import org.example.cafeflow.promotion.repository.PromotionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final MemberRepository memberRepository;
    private final CafeRepository cafeRepository;

    public ResponsePromotionDto registPromotion(RequestPromotionDto promotionDto, Long memberId) { //프로모션 등록
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Cafe cafe = cafeRepository.findById(promotionDto.getCafeId());
        Promotion promotion = promotionDto.toEntity(member, cafe);
        Long promotionId = promotionRepository.save(promotion).getId();
        Promotion promotion1 = promotionRepository.findById(promotionId).get();
        cafe.addPromotion(promotion1);

        return ResponsePromotionDto.builder()
                .id(promotionId)
                .memberId(member.getId())
                .cafeId(cafe.getId())
                .createdAt(promotion.getCreatedAt())
                .startDate(promotionDto.getStartDate())
                .endDate(promotionDto.getEndDate())
                .description(promotionDto.getDescription())
                .image(promotion.getImage())
                .build();

    }
    public List<ResponsePromotionDto> findAllBySort(String sortBy) { //프로모션 목록 확인(정렬)
        List<Promotion> promotions;
        switch (sortBy) {
            case "created-at":
                promotions = promotionRepository.findAllByCreatedAt(); //가장 최근에 만들어진 순서로 조회
                break;
            case "proceeding":
                promotions = promotionRepository.findAllByProceeding(LocalDateTime.now()); //진행중인 프로모션만 조회
                break;
            case "end":
                promotions = promotionRepository.findAllByEnd(LocalDateTime.now()); //끝난 프로모션만 조회
                break;
            case "upcoming":
                promotions = promotionRepository.findAllByUpcoming(LocalDateTime.now()); //진행 예정인 프로모션만 조회
                break;
            default:
                promotions = promotionRepository.findAll(); //전부 다 조회
                break;
        }
        return promotions.stream()
                .map(p -> ResponsePromotionDto.builder()
                        .id(p.getId())
                        .memberId(p.getMember().getId())
                        .cafeId(p.getCafe().getId())
                        .createdAt(p.getCreatedAt())
                        .startDate(p.getStartDate())
                        .endDate(p.getEndDate())
                        .description(p.getDescription())
                        .image(p.getImage())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ResponsePromotionDto> findAllByCafeSort(String sortBy, Long cafeId) { //프로모션 목록 확인(정렬)
        List<Promotion> promotions;
        switch (sortBy) {
            case "created-at":
                promotions = promotionRepository.findAllByCreatedAtByCafe(cafeId); //가장 최근에 만들어진 순서로 조회
                break;
            case "proceeding":
                promotions = promotionRepository.findAllByProceedingByCafe(LocalDateTime.now(), cafeId); //진행중인 프로모션만 조회
                break;
            case "end":
                promotions = promotionRepository.findAllByEndByCafe(LocalDateTime.now(), cafeId); //끝난 프로모션만 조회
                break;
            case "upcoming":
                promotions = promotionRepository.findAllByUpcomingByCafe(LocalDateTime.now(), cafeId); //진행 예정인 프로모션만 조회
                break;
            default:
                promotions = promotionRepository.findAllByCafe(cafeId); //전부 다 조회
                break;
        }
        return promotions.stream()
                .map(p -> ResponsePromotionDto.builder()
                        .id(p.getId())
                        .memberId(p.getMember().getId())
                        .cafeId(p.getCafe().getId())
                        .createdAt(p.getCreatedAt())
                        .startDate(p.getStartDate())
                        .endDate(p.getEndDate())
                        .description(p.getDescription())
                        .image(p.getImage())
                        .build())
                .collect(Collectors.toList());
    }

    //프로모션 수정
    public ResponsePromotionDto updatePromotion(RequestPromotionDto promotionDto, Long memberId, Long promotionId) {
        Promotion p = promotionRepository.findById(promotionId).get();
        if(memberId != p.getMember().getId())
            throw new UnauthorizedAccessException("이 프로모션을 업데이트할 권한이 없습니다.");
        p.updatePromotion(promotionDto.getStartDate(), promotionDto.getEndDate(), promotionDto.getDescription(), promotionDto.getImage());
        return ResponsePromotionDto.builder()
                .id(p.getId())
                .memberId(p.getMember().getId())
                .cafeId(p.getCafe().getId())
                .createdAt(p.getCreatedAt())
                .startDate(p.getStartDate())
                .endDate(p.getEndDate())
                .description(p.getDescription())
                .image(p.getImage())
                .build();
    }

    //프로모션 삭제
    public void deletePromotion(Long memberId, Long promotionId) {
        Promotion p = promotionRepository.findById(promotionId).get();
        if(memberId != p.getMember().getId())
            throw new UnauthorizedAccessException("이 프로모션을 삭제 할 권한이 없습니다.");
        promotionRepository.deleteById(promotionId);

    }
}
