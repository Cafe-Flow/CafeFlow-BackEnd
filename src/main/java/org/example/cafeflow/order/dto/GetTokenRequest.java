package org.example.cafeflow.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTokenRequest {
    private String imp_key;
    private String imp_secret;
}