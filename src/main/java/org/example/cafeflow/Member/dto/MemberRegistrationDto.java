package org.example.cafeflow.Member.dto;

import lombok.*;
import org.example.cafeflow.Member.domain.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRegistrationDto {

    private String username;
    private String nickname;
    private String loginId;
    private String email;
    private String password;
    private Member.Gender gender;
    private Integer age;
    private Long cityId;
    private Long stateId;
}
