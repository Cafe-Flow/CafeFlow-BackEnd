    package org.example.cafeflow.Member.dto;

    import lombok.*;
    import org.example.cafeflow.Member.domain.Member;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class MemberDto {
        // 기본적인 회원 정보 조회에 사용 : ID, 사용자 이름, 닉네임, 이메일, 성별, 나이, 그리고 사용자의 지역 정보(도와 도시)를 포함
        private Long id;
        private String username;
        private String nickname;
        private String email;
        private Member.Gender gender;
        private Integer age;
        private Long cityId;
        private Long stateId;
        private Member.UserType userType;
    }
