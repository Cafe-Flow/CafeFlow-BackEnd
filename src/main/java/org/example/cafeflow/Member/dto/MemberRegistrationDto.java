package org.example.cafeflow.Member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.example.cafeflow.Member.domain.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRegistrationDto {

    // 회원 가입 요청 시 사용 : 사용자 이름, 닉네임, 로그인 ID, 이메일, 비밀번호, 성별, 나이, 그리고 사용자의 지역 정보를 포함
    @NotBlank(message = "사용자 이름을 입력해주세요.")
    @Size(min = 2, max = 5, message = "사용자 이름은 2자 이상, 5자 이하여야 합니다.")
    private String username;

    @NotBlank(message = "사용자 닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "사용자 닉네임은 2자 이상, 10자 이하여야 합니다.")
    private String nickname;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 8, max = 15, message = "사용자 아이디는 8자 이상, 15자 이하여야 합니다.")
    private String loginId;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "성별을 선택해주세요.")
    private Member.Gender gender;

    @NotBlank(message = "사용자 나이를 입력해주세요.")
    private Integer age;

    @NotBlank(message = "사용자 지역(도)을 입력해주세요.")
    private Long stateId;

    @NotBlank(message = "사용자 지역을 입력해주세요.")
    private Long cityId;

    @NotBlank(message = "사용자 유형을 선택해주세요.")
    private Member.UserType userType;
}
