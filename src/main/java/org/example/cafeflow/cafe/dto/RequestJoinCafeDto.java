package org.example.cafeflow.cafe.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.review.domain.Review;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RequestJoinCafeDto {
    private String name;

    private String address;

    private String description;

    private String region;
}
