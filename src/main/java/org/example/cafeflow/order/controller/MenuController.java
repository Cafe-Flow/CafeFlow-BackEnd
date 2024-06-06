package org.example.cafeflow.order.controller;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.order.dto.BasicMenuDto;
import org.example.cafeflow.order.dto.CafeMenuDto;
import org.example.cafeflow.order.service.MenuService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/basic")
    public List<BasicMenuDto> getAllBasicMenus() {
        return menuService.getAllBasicMenus();
    }

    @GetMapping("/cafe/{cafeId}")
    public List<CafeMenuDto> getMenusByCafeId(@PathVariable("cafeId") Long cafeId) {
        return menuService.getMenusByCafeId(cafeId);
    }

    @GetMapping("/search")
    public List<BasicMenuDto> searchMenusByName(@RequestParam("name") String name) {
        return menuService.searchMenusByName(name);
    }

    @PostMapping("/basic")
    public BasicMenuDto addBasicMenu(
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("image") MultipartFile image) throws IOException {
        return menuService.addBasicMenu(name, type, image);
    }

    @PostMapping("/cafe")
    public CafeMenuDto addCafeMenu(@RequestBody CafeMenuDto cafeMenuDto) {
        return menuService.addCafeMenu(cafeMenuDto);
    }

    @PutMapping("/basic/{id}")
    public BasicMenuDto updateBasicMenu(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        return menuService.updateBasicMenu(id, name, type, image);
    }

    @DeleteMapping("/basic/{id}")
    public void deleteBasicMenu(@PathVariable("id") Long id) {
        menuService.deleteBasicMenu(id);
    }

    @PutMapping("/cafe/{id}")
    public CafeMenuDto updateCafeMenu(@PathVariable("id") Long id, @RequestBody CafeMenuDto cafeMenuDto) {
        return menuService.updateCafeMenu(id, cafeMenuDto);
    }

    @DeleteMapping("/cafe/{id}")
    public void deleteCafeMenu(@PathVariable("id") Long id) {
        menuService.deleteCafeMenu(id);
    }
}
