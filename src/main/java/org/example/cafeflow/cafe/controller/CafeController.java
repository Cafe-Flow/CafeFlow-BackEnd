package org.example.cafeflow.cafe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.Member.util.JwtTokenProvider;
import org.example.cafeflow.Member.util.UserPrincipal;
import org.example.cafeflow.cafe.dto.RequestCafeDto;
import org.example.cafeflow.cafe.dto.ResponseCafeDto;
import org.example.cafeflow.cafe.service.CafeService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    //JWT
    private UserPrincipal getCurrentUser(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsername(token);
            Member member = memberRepository.findByLoginId(username)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            return new UserPrincipal(member);
        }
        return null;
    }

    //카페 등록(직접 작성 + 네이버 연동한거 이쪽으로 보내기)
    @PostMapping("/api/register-cafe")
    public Long joinCafe(HttpServletRequest request, @Valid @RequestBody RequestCafeDto cafeDto) {
        UserPrincipal currentUser = getCurrentUser(request);
        Long cafeId = cafeService.join(cafeDto, currentUser.getId());
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