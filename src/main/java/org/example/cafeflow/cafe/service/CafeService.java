package org.example.cafeflow.cafe.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.dto.RequestJoinCafeDto;
import org.example.cafeflow.cafe.dto.ResponseCafeDto;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeService {
    private final CafeRepository cafeRepository;

    public Long join(RequestJoinCafeDto cafeDto) {
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
                        .reviewCount(c.getReviewsCount())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<ResponseCafeDto> findByName(String name) {
        List<Cafe> cafes = cafeRepository.findByName(name);
        return cafes.stream()
                .map(c -> ResponseCafeDto.builder()
                        .name(c.getName())
                        .reviewCount(c.getReviewsCount())
                        .build()
                )
                .collect(Collectors.toList());

    }

    public ResponseCafeDto findByIdForCafeInfo(Long id) {
        Cafe cafe = cafeRepository.findById(id);
        return ResponseCafeDto.builder()
                .name(cafe.getName())
                .address(cafe.getAddress())
                .reviewCount(cafe.getReviewsCount())
                .description(cafe.getDescription())
                .region(cafe.getRegion())
                .createdAt(cafe.getCreatedAt())
                .build();
    }
}
