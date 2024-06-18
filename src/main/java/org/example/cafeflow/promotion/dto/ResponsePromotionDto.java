package org.example.cafeflow.promotion.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResponsePromotionDto {

    private Long id;
    private Long memberId;
    private Long cafeId;
    private String cafeName;
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private byte[] image;

}
