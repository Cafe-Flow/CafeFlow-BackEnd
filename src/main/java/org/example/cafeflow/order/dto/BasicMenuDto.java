package org.example.cafeflow.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicMenuDto {
    private Long id;
    private String name;
    private String type;
    private byte[] image;
}
