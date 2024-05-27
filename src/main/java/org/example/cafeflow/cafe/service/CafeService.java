package org.example.cafeflow.cafe.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.dto.RequestCafeDto;
import org.example.cafeflow.cafe.dto.ResponseCafeDto;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeService {
    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;

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
                        .reviewsRating(c.getReviewsRating())
                        .reviewCount(c.getReviewsCount())
                        .mapx(c.getMapx())
                        .mapy(c.getMapy())
                        .watingTime(c.getWatingTime())
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
                        cafeDto.getAddress(),
                        cafeDto.getDescription(),
                        cafeDto.getMapx(),
                        cafeDto.getMapy(),
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
                .reviewCount(cafe.getReviewsCount())
                .reviewsRating(cafe.getReviewsRating())
                .description(cafe.getDescription())
                .mapy(cafe.getMapy())
                .mapy(cafe.getMapy())
                .image(cafe.getImage())
                .createdAt(cafe.getCreatedAt())
                .updatedAt(cafe.getUpdatedAt())
                .build();
    }
}
