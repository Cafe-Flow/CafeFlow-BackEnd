package org.example.cafeflow.cafe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.cafeflow.cafe.domain.Cafe;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor //builder 패턴은 전체필드 생성자가 필요함
@Builder
public class ResponseCafeListDto {
    private String name;
    private int reviewsCount;

}
