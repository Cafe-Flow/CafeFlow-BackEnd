package org.example.cafeflow.cafe.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.cafeflow.cafe.domain.Cafe;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RequestCafeDto {
    @NotBlank(message = "카페 이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    private String description;

    private Integer mapx;

    private Integer mapy;

    private MultipartFile image;



    @Builder
    public RequestCafeDto(String name,
                          String address,
                          String description,
                          Integer mapx,
                          Integer mapy,
                          MultipartFile image) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.mapx = mapx;
        this.mapy = mapy;
        this.image = image;
    }

    public Cafe toEntity() {
        LocalDateTime time = LocalDateTime.now();

        byte[] imageBytes = null;
        try {
            imageBytes = image != null ? image.getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("이미지 변환 중 오류가 발생했습니다.", e);
        }

        Cafe cafe = Cafe.builder()
                .name(name)
                .address(address)
                .reviewsCount(0)
                .description(description)
                .mapx(mapx)
                .mapy(mapy)
                .image(imageBytes)
                .createdAt(time)
                .updatedAt(time)
                .build();
        return cafe;
    }
}
