package org.example.cafeflow.cafe.controller;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.dto.RequestJoinCafeDto;
import org.example.cafeflow.cafe.dto.ResponseCafeDto;
import org.example.cafeflow.cafe.service.CafeService;
import org.springframework.web.bind.annotation.*;

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
    public List<ResponseCafeDto> cafeList() {
        return cafeService.findAll();
    }


//    카페 목록 확인 (검색)
//    @GetMapping("/cafe")
//    public List<ResponseCafeDto> searchNameCafeList(@RequestBody String name) {
//        return cafeService.findByName(name);
//    }

    //카페 목록 확인 (리뷰 순)
//    @GetMapping("/cafe/review-seq")
//    public List<ResponseCafeDto> reviewSeqCafeList(String name) {
//        return cafeService.findAll();
//    }



    //카페 목록 확인 (등록 시간 순)

    //카페 정보 확인
    @GetMapping("/cafe/{cafe_id}")
    public ResponseCafeDto cafeInfo(@PathVariable("cafe_id") Long cafeId) {
        return cafeService.findByIdForCafeInfo(cafeId);
    }
}