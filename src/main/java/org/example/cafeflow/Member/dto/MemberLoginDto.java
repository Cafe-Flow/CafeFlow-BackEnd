package org.example.cafeflow.Member.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLoginDto {
    // 로그인 요청 시 사용 : 사용자 아이디와 비밀번호를 검증하기 위한 필드를 포함
    @NotBlank(message = "아이디 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
