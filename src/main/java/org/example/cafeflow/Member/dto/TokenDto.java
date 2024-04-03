package org.example.cafeflow.Member.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    // 인증 성공 시 사용자에게 반환되는 JWT 토큰을 담는 데 사용
    private String token;
}
