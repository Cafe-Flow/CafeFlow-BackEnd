package org.example.cafeflow.cafe.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.domain.Traffic;
import org.example.cafeflow.cafe.dto.RequestCafeDto;
import org.example.cafeflow.cafe.dto.ResponseCafeDto;
import org.example.cafeflow.cafe.dto.TrafficDto;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.example.cafeflow.promotion.dto.ResponsePromotionDto;
import org.example.cafeflow.seat.repository.SeatRepository;
import org.example.cafeflow.seat.repository.UseSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeService {
    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;
    private final UseSeatRepository useSeatRepository;
    private final SeatRepository seatRepository;

    public Long join(RequestCafeDto cafeDto, Long userId) {
        Cafe cafe = cafeDto.toEntity();
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        cafe.registerUser(member);
        cafeRepository.save(cafe);
        return cafe.getId();
    }

    //카페 목록 전부 보기
    public List<ResponseCafeDto> findAllBySort(String sortBy) {
        List<Cafe> cafes;
        switch (sortBy) {
            case "created-at":
                cafes = cafeRepository.findAllByCreatedAt();
                break;
            case "reviews-count":
                cafes = cafeRepository.findAllByReviewsCount();
                break;
            case "reviews-rating":
                cafes = cafeRepository.findAllByReviewsRating();
                break;
            default:
                cafes = cafeRepository.findAll();
                break;
        }

        return cafes.stream()
                .map(c -> ResponseCafeDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .address(c.getAddress())
                        .memberId(c.getMember().getId())
                        .reviewsRating(c.getReviewsRating())
                        .reviewCount(c.getReviewsCount())
                        .description(c.getDescription())
                        .mapx(c.getMapx())
                        .mapy(c.getMapy())
                        .watingTime(c.getWatingTime())
                        .traffic(c.getTraffic())
                        .image(c.getImage())
                        .build()
                )
                .collect(Collectors.toList());
    }


    //카페 정보 수정하기
    public void updateCafe(Long id, RequestCafeDto cafeDto) {
        Cafe cafe = cafeRepository.findById(id);
        LocalDateTime updatedAt = LocalDateTime.now();
        byte[] imageBytes = null;
        try {
            imageBytes = cafeDto.getImage() != null ? cafeDto.getImage().getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("이미지 변환 중 오류가 발생했습니다.", e);
        }
        cafe.updateCafe(cafeDto.getName(),
                        cafeDto.getDescription(),
                        imageBytes,
                        updatedAt
        );
    }

    //카페 정보 삭제하기
    public void deleteCafe(Long id) {
        cafeRepository.delete(id);
    }


    //카페 이름 검색
    public List<ResponseCafeDto> findByName(String name) {
        List<Cafe> cafes = cafeRepository.findByName(name);
        return cafes.stream()
                .map(c -> ResponseCafeDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .reviewCount(c.getReviewsCount())
                        .build()
                )
                .collect(Collectors.toList());
    }


    //카페 정보 확인
    public ResponseCafeDto findByIdForCafeInfo(Long id) {
        Cafe cafe = cafeRepository.findById(id);
        return ResponseCafeDto.builder()
                .id(cafe.getId())
                .name(cafe.getName())
                .address(cafe.getAddress())
                .memberId(cafe.getMember().getId())
                .reviewCount(cafe.getReviewsCount())
                .reviewsRating(cafe.getReviewsRating())
                .description(cafe.getDescription())
                .mapx(cafe.getMapx())
                .mapy(cafe.getMapy())
                .image(cafe.getImage())
                .watingTime(cafe.getWatingTime())
                .traffic(cafe.getTraffic())
                .createdAt(cafe.getCreatedAt())
                .updatedAt(cafe.getUpdatedAt())
                .build();
    }

    public void updateCafeTraffic(Long cafeId, TrafficDto trafficDto) {
        Cafe cafe = cafeRepository.findById(cafeId);
        cafe.updateTraffic(trafficDto.getTraffic());
    }

    public Traffic trafficIsUpdate(Long cafeId) {
        int useSeatSize = useSeatRepository.findUsingSeatNumber(cafeId);
        int fullSeatSize = seatRepository.findFullSeatNumber(cafeId);

        //cafe의 Traffic이 변한다면 true 반환
        double occupancyRatio = (double) useSeatSize / fullSeatSize * 100; //비율

        Cafe cafe = cafeRepository.findById(cafeId);
        Traffic oldTraffic = cafe.getTraffic();


        Traffic newTraffic;
        if (occupancyRatio <= 30) {
            newTraffic = Traffic.GREEN;
        } else if (occupancyRatio <= 70) {
            newTraffic = Traffic.YELLOW;
        } else {
            newTraffic = Traffic.RED;
        }
        cafe.updateTraffic(newTraffic);
        boolean isTrafficUpdated = oldTraffic != newTraffic;

        if (isTrafficUpdated)
            return newTraffic;
        else
            return null;



    }

    public List<ResponseCafeDto> findMyCafes(Long user_id) {
        List<Cafe> cafes = cafeRepository.findMyCafes(user_id);
        return cafes.stream()
                .map(c -> ResponseCafeDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .address(c.getAddress())
                        .memberId(c.getMember().getId())
                        .reviewCount(c.getReviewsCount())
                        .reviewsRating(c.getReviewsRating())
                        .description(c.getDescription())
                        .mapx(c.getMapx())
                        .mapy(c.getMapy())
                        .image(c.getImage())
                        .watingTime(c.getWatingTime())
                        .traffic(c.getTraffic())
                        .createdAt(c.getCreatedAt())
                        .updatedAt(c.getUpdatedAt())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
