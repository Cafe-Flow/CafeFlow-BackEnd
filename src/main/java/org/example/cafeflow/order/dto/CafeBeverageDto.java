package org.example.cafeflow.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CafeBeverageDto {
    private Long id;
    private Long basicBeverageId;
    private String basicBeverageName;
    private Long cafeId;
    private Double price;
}
