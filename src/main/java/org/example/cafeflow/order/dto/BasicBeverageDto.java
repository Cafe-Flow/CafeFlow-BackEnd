package org.example.cafeflow.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicBeverageDto {
    private Long id;
    private String name;
    private String description;
    private byte[] image;
}
