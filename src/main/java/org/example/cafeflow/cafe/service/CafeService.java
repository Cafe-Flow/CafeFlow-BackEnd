package org.example.cafeflow.cafe.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.dto.RequestJoinCafeDto;
import org.example.cafeflow.cafe.dto.ResponseCafeListDto;
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

    public Long join(RequestJoinCafeDto cafeDto) {
        Cafe cafe = cafeDto.toEntity();
        cafeRepository.save(cafe);

        return cafe.getId();
    }
    public List<ResponseCafeListDto> findAll() {
        List<Cafe> cafes = cafeRepository.findAll();
        return cafes.stream()
                .map(c -> ResponseCafeListDto.builder()
                        .name(c.getName())
                        .reviewsCount(c.getReviewsCount())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<ResponseCafeListDto> findByName(String name) {
        List<Cafe> cafes = cafeRepository.findByName(name);
        return cafes.stream()
                .map(c -> ResponseCafeListDto.builder()
                        .name(c.getName())
                        .reviewsCount(c.getReviewsCount())
                        .build()
                )
                .collect(Collectors.toList());

    }
}
