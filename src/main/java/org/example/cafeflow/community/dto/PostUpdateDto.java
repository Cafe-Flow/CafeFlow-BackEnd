package org.example.cafeflow.community.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {
    @NotBlank(message = "게시글 제목을 입력해야 합니다.")
    private String title;

    @NotBlank(message = "게시글 내용을 입력해야 합니다.")
    private String content;

    private MultipartFile image;

    @NotNull(message = "지역 ID는 업데이트 시 필수입니다.")
    private Long stateId;
}
