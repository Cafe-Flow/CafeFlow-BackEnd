package org.example.cafeflow.cafe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cafeflow.cafe.dto.RequestCafeDto;
import org.example.cafeflow.cafe.dto.ResponseCafeDto;
import org.example.cafeflow.cafe.service.CafeService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;


    //카페 등록(직접 작성 또는 네이버 연동에서 이쪽으로)
    @PostMapping("/api/register-cafe")
    public Long joinCafe(@Valid @RequestBody RequestCafeDto cafeDto) {
        Long cafeId = cafeService.join(cafeDto);
        return cafeId;
    }

    //카페 등록(네이버 연동)
    @GetMapping("/api/register-cafe")
    public ResponseEntity<String> joinCafeFromNaver(@RequestParam("search") String search) {
        String query = search + " 카페";
        //String encode = Base64.getEncoder().encodeToString(query.getBytes(StandardCharsets.UTF_8));

        URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com/")
                .path("v1/search/local.json")
                .queryParam("query", query) // 검색어
                .queryParam("display", 100) //몇개 노출
                .queryParam("start", 1)
                .queryParam("sort", "random")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();

        log.info("uri : {}", uri);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", "_ezXdGwXAqGvflG7Tvta");
        headers.set("X-Naver-Client-Secret", "m0nzDklPPy");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        return ResponseEntity.ok().body(response.getBody());
    }

    //카페 목록 확인
    @GetMapping("/api/cafe")
    public List<ResponseCafeDto> cafeList(@RequestParam("sort-by") String sortBy) {
        return cafeService.findAllBySort(sortBy);
    }

    //카페 정보 확인
    @GetMapping("/api/cafe/{cafe_id}")
    public ResponseCafeDto cafeInfo(@PathVariable("cafe_id") Long cafeId) {
        return cafeService.findByIdForCafeInfo(cafeId);
    }

    //카페 정보 수정
    @PutMapping("/api/cafe/{cafe_id}")
    public void updateCafe(@PathVariable("cafe_id") Long cafeId, @RequestBody RequestCafeDto cafeDto) {
        cafeService.updateCafe(cafeId, cafeDto);
    }

    //카페 정보 삭제
    @DeleteMapping("/api/cafe/{cafe_id}")
    public void deleteCafe(@PathVariable("cafe_id") Long cafeId) {
        cafeService.deleteCafe(cafeId);
    }
}