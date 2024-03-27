package org.example.cafeflow.Member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLoginDto {

    private String loginId;
    private String password;
}
