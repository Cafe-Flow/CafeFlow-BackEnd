package org.example.cafeflow.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanSharingDto {
    private Long userId;
    private String details;
    private LocalDateTime visitTime;
}
