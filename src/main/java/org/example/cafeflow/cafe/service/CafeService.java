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
@Transactional()
public class CafeService {
    private final CafeRepository cafeRepository;

    public Long join(RequestJoinCafeDto cafeDto) {
        LocalDateTime time = LocalDateTime.now();
        Cafe cafe = Cafe.builder()
                .name(cafeDto.getName())
                .address(cafeDto.getAddress())
                .reviewsCount(0)
                .description(cafeDto.getDescription())
                .region(cafeDto.getRegion())
                .createdAt(time)
                .updatedAt(time)
                .build();
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

    public Cafe findById(Long cafeId) {
        return cafeRepository.findById(cafeId);
    }
}
