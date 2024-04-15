package org.example.cafeflow.community.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    private String from;
    // getter 메서드 추가
    @Getter
    private String to;
    private String text;
    private String time;

}
