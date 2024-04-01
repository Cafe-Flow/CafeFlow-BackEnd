package org.example.cafeflow.cafe.controller;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.dto.RequestJoinCafeDto;
import org.example.cafeflow.cafe.dto.ResponseCafeListDto;
import org.example.cafeflow.cafe.service.CafeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;


    //카페 등록
    @PostMapping("/join-cafe")
    public RequestJoinCafeDto joinCafe(@RequestBody RequestJoinCafeDto cafeDto) {
        cafeService.join(cafeDto);
        return cafeDto;
    }

    //카페 목록 확인 (기본)
    @GetMapping("/cafe")
    public List<ResponseCafeListDto> cafeList() {
        List<ResponseCafeListDto> cafeDto = cafeService.findAll();
        return cafeDto;
    }


//    카페 목록 확인 (검색)
//    @GetMapping("/cafe")
//    public List<Cafe> searchCafeList(String name) {
//
//    }

    //카페 목록 확인 (리뷰 순)
    @GetMapping("/cafe/review-seq")
    public List<ResponseCafeListDto> reviewSeqCafeList(String name) {
        return cafeService.findAll();
    }



    //카페 목록 확인 (등록 시간 순)
}