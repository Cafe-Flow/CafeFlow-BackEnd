package org.example.cafeflow.Member.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.example.cafeflow.Member.domain.Member;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRegistrationDto {
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

    @NotNull(message = "성별을 선택해주세요.")
    private Member.Gender gender;

    @NotNull(message = "사용자 나이를 입력해주세요.")
    private Integer age;

    @NotNull(message = "도를 선택해주세요.")
    @Min(1)
    private Long stateId;

    @NotNull(message = "시를 선택해주세요.")
    @Min(1)
    private Long cityId;

    @NotNull(message = "사용자 유형을 선택해주세요.")
    private Member.UserType userType;

    private MultipartFile image;
}
