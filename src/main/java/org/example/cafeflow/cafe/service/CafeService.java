package org.example.cafeflow.cafe.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.domain.CafeCoordinates;
import org.example.cafeflow.cafe.dto.RequestCafeDto;
import org.example.cafeflow.cafe.dto.ResponseCafeDto;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeService {
    private final CafeRepository cafeRepository;

    public Long join(RequestCafeDto cafeDto) {
        Cafe cafe = cafeDto.toEntity();
        System.out.println(cafeDto.getCafeCoordinates().getMapX());
        System.out.println(cafe.getCafeCoordinates().getMapX());
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
                        .build()
                )
                .collect(Collectors.toList());
    }


    //카페 정보 수정하기
    public void updateCafe(Long id, RequestCafeDto cafeDto) {
        Cafe cafe = cafeRepository.findById(id);
        LocalDateTime updatedAt = LocalDateTime.now();
        cafe.updateCafe(cafeDto.getName(),
                        cafeDto.getAddress(),
                        cafeDto.getDescription(),
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
                .cafeCoordinates(cafe.getCafeCoordinates())
                .createdAt(cafe.getCreatedAt())
                .updatedAt(cafe.getUpdatedAt())
                .build();
    }
}
