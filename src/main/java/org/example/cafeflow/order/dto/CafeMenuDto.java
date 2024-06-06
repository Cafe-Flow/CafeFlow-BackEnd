package org.example.cafeflow.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CafeMenuDto {
    private Long id;
    private Long basicMenuId;
    private String basicMenuName;
    private Long cafeId;
    private Double price;
}
