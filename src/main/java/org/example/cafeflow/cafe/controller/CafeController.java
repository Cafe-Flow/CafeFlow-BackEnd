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
    @PostMapping("/join-cafe")
    public Long joinCafe(@RequestBody RequestCafeDto cafeDto) {
        Long id = cafeService.join(cafeDto);
        return id;
    }

    //카페 목록 확인 (기본)
    @GetMapping("/cafe")
    public List<ResponseCafeDto> cafeList() {
        return cafeService.findAll();
    }

    //카페 정보 확인
    @GetMapping("/cafe/{cafe_id}")
    public ResponseCafeDto cafeInfo(@PathVariable("cafe_id") Long cafeId) {
        return cafeService.findByIdForCafeInfo(cafeId);
    }

    //카페 정보 수정
    @PostMapping("/cafe/{cafe_id}")
    public void cafeUpdate(@PathVariable("cafe_id") Long cafeId, @RequestBody RequestCafeDto cafeDto) {
        cafeService.updateCafe(cafeId, cafeDto);
    }


    //카페 리뷰 확인
    @GetMapping("/cafe/{cafe_id}/review")
    public ResponseCafeDto cafeReview(@PathVariable("cafe_id") Long cafeId) {
        return cafeService.findByIdForCafeInfo(cafeId);
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