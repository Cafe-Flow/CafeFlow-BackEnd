package org.example.cafeflow.cafe.controller;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.dto.RequestCafeDto;
import org.example.cafeflow.cafe.dto.ResponseCafeDto;
import org.example.cafeflow.cafe.service.CafeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;


    //카페 등록
    @PostMapping("/api/register-cafe")
    public Long joinCafe(@RequestBody RequestCafeDto cafeDto) {
        Long cafeId = cafeService.join(cafeDto);
        return cafeId;
    }

    //카페 목록 확인 (기본)
    @GetMapping("/api/cafe")
    public List<ResponseCafeDto> cafeList() {
        return cafeService.findAll();
    }

    //카페 정보 확인
    @GetMapping("/api/cafe/{cafe_id}")
    public ResponseCafeDto cafeInfo(@PathVariable("cafe_id") Long cafeId) {
        return cafeService.findByIdForCafeInfo(cafeId);
    }

    //카페 정보 수정
    @PostMapping("/api/cafe/{cafe_id}")
    public void cafeUpdate(@PathVariable("cafe_id") Long cafeId, @RequestBody RequestCafeDto cafeDto) {
        cafeService.updateCafe(cafeId, cafeDto);
    }


//    카페 목록 확인 (검색)
//    @GetMapping("/cafe")
//    public List<ResponseCafeDto> searchNameCafeList(@RequestBody String name) {
//        return cafeService.findByName(name);
//    }

//    카페 목록 확인 (리뷰 순)
//    @GetMapping("/cafe/review-seq")
//    public List<ResponseCafeDto> reviewSeqCafeList(String name) {
//        return cafeService.findAll();
//    }



    //카페 목록 확인 (등록 시간 순)


}