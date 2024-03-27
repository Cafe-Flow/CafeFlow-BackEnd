    package org.example.cafeflow.Member.dto;

    import lombok.*;
    import org.example.cafeflow.Member.domain.Member;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class MemberDto {

        private Long id;
        private String username;
        private String nickname;
        private String email;
        private Member.Gender gender;
        private Integer age;
        private Long cityId;
        private Long stateId;
    }
