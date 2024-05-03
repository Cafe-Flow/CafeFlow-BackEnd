package org.example.cafeflow.community.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardToPostDto {
    private List<BoardIntoPostDto> posts;
}
