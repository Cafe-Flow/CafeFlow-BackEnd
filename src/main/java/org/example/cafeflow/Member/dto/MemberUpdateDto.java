package org.example.cafeflow.Member.dto;

import lombok.*;
import org.example.cafeflow.Member.domain.Member;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberUpdateDto {
    private String username;
    private String nickname;
    private String email;
    private Member.Gender gender;
    private Integer age;
    private Long stateId;
    private Long cityId;
    private Member.UserType userType;
    private MultipartFile image;
}
