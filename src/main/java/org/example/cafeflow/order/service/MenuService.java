package org.example.cafeflow.order.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.cafe.domain.Cafe;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.example.cafeflow.order.domain.BasicMenu;
import org.example.cafeflow.order.domain.CafeMenu;
import org.example.cafeflow.order.dto.BasicMenuDto;
import org.example.cafeflow.order.dto.CafeMenuDto;
import org.example.cafeflow.order.repository.BasicMenuRepository;
import org.example.cafeflow.order.repository.CafeMenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final BasicMenuRepository basicMenuRepository;
    private final CafeMenuRepository cafeMenuRepository;
    private final CafeRepository cafeRepository;

    public List<BasicMenuDto> getAllBasicMenus() {
        return basicMenuRepository.findAll().stream()
                .map(menu -> new BasicMenuDto(
                        menu.getId(),
                        menu.getName(),
                        menu.getType(),
                        menu.getImage()))
                .collect(Collectors.toList());
    }

    public List<CafeMenuDto> getMenusByCafeId(Long cafeId) {
        return cafeMenuRepository.findByCafeId(cafeId).stream()
                .map(menu -> new CafeMenuDto(
                        menu.getId(),
                        menu.getBasicMenu().getId(),
                        menu.getBasicMenu().getName(),
                        menu.getCafe().getId(),
                        menu.getPrice()))
                .collect(Collectors.toList());
    }

    public List<BasicMenuDto> searchMenusByName(String name) {
        return basicMenuRepository.findByNameContaining(name).stream()
                .map(menu -> new BasicMenuDto(
                        menu.getId(),
                        menu.getName(),
                        menu.getType(),
                        menu.getImage()))
                .collect(Collectors.toList());
    }

    public BasicMenuDto addBasicMenu(String name, String type, MultipartFile image) throws IOException {
        BasicMenu basicMenu = BasicMenu.builder()
                .name(name)
                .type(type)
                .image(image.getBytes())
                .build();
        basicMenu = basicMenuRepository.save(basicMenu);
        return new BasicMenuDto(
                basicMenu.getId(),
                basicMenu.getName(),
                basicMenu.getType(),
                basicMenu.getImage());
    }

    public CafeMenuDto addCafeMenu(CafeMenuDto cafeMenuDto) {
        BasicMenu basicMenu = basicMenuRepository.findById(cafeMenuDto.getBasicMenuId())
                .orElseThrow(() -> new IllegalArgumentException("Basic Menu not found"));
        Cafe cafe = cafeRepository.findById(cafeMenuDto.getCafeId());
        if (cafe == null) {
            throw new IllegalArgumentException("Cafe not found");
        }

        CafeMenu cafeMenu = CafeMenu.builder()
                .basicMenu(basicMenu)
                .cafe(cafe)
                .price(cafeMenuDto.getPrice())
                .build();

        cafeMenu = cafeMenuRepository.save(cafeMenu);

        return new CafeMenuDto(
                cafeMenu.getId(),
                cafeMenu.getBasicMenu().getId(),
                cafeMenu.getBasicMenu().getName(),
                cafeMenu.getCafe().getId(),
                cafeMenu.getPrice()
        );
    }

    public BasicMenuDto updateBasicMenu(Long id, String name, String type, MultipartFile image) throws IOException {
        BasicMenu basicMenu = basicMenuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Basic Menu not found"));
        basicMenu.setName(name);
        basicMenu.setType(type);
        if (image != null) {
            basicMenu.setImage(image.getBytes());
        }
        basicMenu = basicMenuRepository.save(basicMenu);
        return new BasicMenuDto(
                basicMenu.getId(),
                basicMenu.getName(),
                basicMenu.getType(),
                basicMenu.getImage());
    }

    public void deleteBasicMenu(Long id) {
        basicMenuRepository.deleteById(id);
    }

    public CafeMenuDto updateCafeMenu(Long id, CafeMenuDto cafeMenuDto) {
        CafeMenu cafeMenu = cafeMenuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cafe Menu not found"));
        BasicMenu basicMenu = basicMenuRepository.findById(cafeMenuDto.getBasicMenuId())
                .orElseThrow(() -> new IllegalArgumentException("Basic Menu not found"));
        Cafe cafe = cafeRepository.findById(cafeMenuDto.getCafeId());
        if (cafe == null) {
            throw new IllegalArgumentException("Cafe not found");
        }
        cafeMenu.setBasicMenu(basicMenu);
        cafeMenu.setCafe(cafe);
        cafeMenu.setPrice(cafeMenuDto.getPrice());
        cafeMenu = cafeMenuRepository.save(cafeMenu);
        return new CafeMenuDto(
                cafeMenu.getId(),
                cafeMenu.getBasicMenu().getId(),
                cafeMenu.getBasicMenu().getName(),
                cafeMenu.getCafe().getId(),
                cafeMenu.getPrice());
    }

    public void deleteCafeMenu(Long id) {
        cafeMenuRepository.deleteById(id);
    }
}
