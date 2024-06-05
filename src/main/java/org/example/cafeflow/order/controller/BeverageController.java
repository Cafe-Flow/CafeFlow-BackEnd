package org.example.cafeflow.order.controller;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.order.dto.BasicBeverageDto;
import org.example.cafeflow.order.dto.CafeBeverageDto;
import org.example.cafeflow.order.service.BeverageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/beverages")
@RequiredArgsConstructor
public class BeverageController {
    private final BeverageService beverageService;

    @GetMapping("/basic")
    public List<BasicBeverageDto> getAllBasicBeverages() {
        return beverageService.getAllBasicBeverages();
    }

    @GetMapping("/cafe/{cafeId}")
    public List<CafeBeverageDto> getBeveragesByCafeId(@PathVariable("cafeId") Long cafeId) {
        return beverageService.getBeveragesByCafeId(cafeId);
    }

    @GetMapping("/search")
    public List<BasicBeverageDto> searchBeveragesByName(@RequestParam("name") String name) {
        return beverageService.searchBeveragesByName(name);
    }

    @PostMapping("/basic")
    public BasicBeverageDto addBasicBeverage(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) throws IOException {
        return beverageService.addBasicBeverage(name, description, image);
    }

    @PostMapping("/cafe")
    public CafeBeverageDto addCafeBeverage(@RequestBody CafeBeverageDto cafeBeverageDto) {
        return beverageService.addCafeBeverage(cafeBeverageDto);
    }
}
