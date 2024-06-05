package org.example.cafeflow.order.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.example.cafeflow.order.domain.BasicBeverage;
import org.example.cafeflow.order.domain.CafeBeverage;
import org.example.cafeflow.order.dto.BasicBeverageDto;
import org.example.cafeflow.order.dto.CafeBeverageDto;
import org.example.cafeflow.order.repository.BasicBeverageRepository;
import org.example.cafeflow.order.repository.CafeBeverageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeverageService {
    private final BasicBeverageRepository basicBeverageRepository;
    private final CafeBeverageRepository cafeBeverageRepository;
    private final CafeRepository cafeRepository;

    public List<BasicBeverageDto> getAllBasicBeverages() {
        return basicBeverageRepository.findAll().stream()
                .map(beverage -> new BasicBeverageDto(
                        beverage.getId(),
                        beverage.getName(),
                        beverage.getDescription(),
                        beverage.getImage()))
                .collect(Collectors.toList());
    }

    public List<CafeBeverageDto> getBeveragesByCafeId(Long cafeId) {
        return cafeBeverageRepository.findByCafeId(cafeId).stream()
                .map(beverage -> new CafeBeverageDto(
                        beverage.getId(),
                        beverage.getBasicBeverage().getId(),
                        beverage.getBasicBeverage().getName(),
                        beverage.getCafe().getId(),
                        beverage.getPrice()))
                .collect(Collectors.toList());
    }

    public List<BasicBeverageDto> searchBeveragesByName(String name) {
        return basicBeverageRepository.findByNameContaining(name).stream()
                .map(beverage -> new BasicBeverageDto(
                        beverage.getId(),
                        beverage.getName(),
                        beverage.getDescription(),
                        beverage.getImage()))
                .collect(Collectors.toList());
    }

    public BasicBeverageDto addBasicBeverage(String name, String description, MultipartFile image) throws IOException {
        BasicBeverage basicBeverage = BasicBeverage.builder()
                .name(name)
                .description(description)
                .image(image.getBytes())
                .build();
        basicBeverage = basicBeverageRepository.save(basicBeverage);
        return new BasicBeverageDto(
                basicBeverage.getId(),
                basicBeverage.getName(),
                basicBeverage.getDescription(),
                basicBeverage.getImage());
    }

    public CafeBeverageDto addCafeBeverage(CafeBeverageDto cafeBeverageDto) {
        BasicBeverage basicBeverage = basicBeverageRepository.findById(cafeBeverageDto.getBasicBeverageId())
                .orElseThrow(() -> new IllegalArgumentException("Basic Beverage not found"));
        Cafe cafe = cafeRepository.findById(cafeBeverageDto.getCafeId());
        if (cafe == null) {
            throw new IllegalArgumentException("Cafe not found");
        }

        CafeBeverage cafeBeverage = CafeBeverage.builder()
                .basicBeverage(basicBeverage)
                .cafe(cafe)
                .price(cafeBeverageDto.getPrice())
                .build();

        cafeBeverage = cafeBeverageRepository.save(cafeBeverage);

        return new CafeBeverageDto(
                cafeBeverage.getId(),
                cafeBeverage.getBasicBeverage().getId(),
                cafeBeverage.getBasicBeverage().getName(),
                cafeBeverage.getCafe().getId(),
                cafeBeverage.getPrice()
        );
    }

    public BasicBeverageDto updateBasicBeverage(Long id, String name, String description, MultipartFile image) throws IOException {
        BasicBeverage basicBeverage = basicBeverageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Basic Beverage not found"));
        basicBeverage.setName(name);
        basicBeverage.setDescription(description);
        if (image != null) {
            basicBeverage.setImage(image.getBytes());
        }
        basicBeverage = basicBeverageRepository.save(basicBeverage);
        return new BasicBeverageDto(
                basicBeverage.getId(),
                basicBeverage.getName(),
                basicBeverage.getDescription(),
                basicBeverage.getImage());
    }

    public void deleteBasicBeverage(Long id) {
        basicBeverageRepository.deleteById(id);
    }

    public CafeBeverageDto updateCafeBeverage(Long id, CafeBeverageDto cafeBeverageDto) {
        CafeBeverage cafeBeverage = cafeBeverageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cafe Beverage not found"));
        BasicBeverage basicBeverage = basicBeverageRepository.findById(cafeBeverageDto.getBasicBeverageId())
                .orElseThrow(() -> new IllegalArgumentException("Basic Beverage not found"));
        Cafe cafe = cafeRepository.findById(cafeBeverageDto.getCafeId());
        if (cafe == null) {
            throw new IllegalArgumentException("Cafe not found");
        }
        cafeBeverage.setBasicBeverage(basicBeverage);
        cafeBeverage.setCafe(cafe);
        cafeBeverage.setPrice(cafeBeverageDto.getPrice());
        cafeBeverage = cafeBeverageRepository.save(cafeBeverage);
        return new CafeBeverageDto(
                cafeBeverage.getId(),
                cafeBeverage.getBasicBeverage().getId(),
                cafeBeverage.getBasicBeverage().getName(),
                cafeBeverage.getCafe().getId(),
                cafeBeverage.getPrice());
    }

    public void deleteCafeBeverage(Long id) {
        cafeBeverageRepository.deleteById(id);
    }
}
