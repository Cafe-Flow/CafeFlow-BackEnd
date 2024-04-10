package org.example.cafeflow.cafe.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.dto.RequestCafeDto;
import org.example.cafeflow.cafe.dto.ResponseCafeDto;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeService {
    private final CafeRepository cafeRepository;

    public Long join(RequestCafeDto cafeDto) {
        Cafe cafe = cafeDto.toEntity();
        cafeRepository.save(cafe);

        return cafe.getId();
    }
    public List<ResponseCafeDto> findAll() {
        List<Cafe> cafes = cafeRepository.findAll();
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

    public void updateCafe(Long id, RequestCafeDto cafeDto) {
        Cafe cafe = cafeRepository.findById(id);
        LocalDateTime updatedAt = LocalDateTime.now();
        cafe.updateCafe(cafeDto.getName(),
                        cafeDto.getAddress(),
                        cafeDto.getDescription(),
                        cafeDto.getRegion(),
                        updatedAt
        );
    }

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

    public ResponseCafeDto findByIdForCafeInfo(Long id) {
        Cafe cafe = cafeRepository.findById(id);
        return ResponseCafeDto.builder()
                .id(cafe.getId())
                .name(cafe.getName())
                .address(cafe.getAddress())
                .reviewCount(cafe.getReviewsCount())
                .reviewsRating(cafe.getReviewsRating())
                .description(cafe.getDescription())
                .region(cafe.getRegion())
                .createdAt(cafe.getCreatedAt())
                .updatedAt(cafe.getUpdatedAt())
                .build();
    }
}
