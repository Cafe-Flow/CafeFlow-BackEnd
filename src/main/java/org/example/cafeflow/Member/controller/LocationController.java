package org.example.cafeflow.Member.controller;

import org.example.cafeflow.Member.dto.CityDto;
import org.example.cafeflow.Member.dto.StateDto;
import org.example.cafeflow.Member.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping("/states")
    public ResponseEntity<List<StateDto>> getAllStates() {
        List<StateDto> states = locationService.getAllStates();
        return ResponseEntity.ok(states);
    }

    @GetMapping("/states/{stateId}/cities")
    public ResponseEntity<List<CityDto>> getCitiesByStateId(@PathVariable("stateId") Long stateId) {
        List<CityDto> cityDtos = locationService.getCitiesByStateId(stateId);
        return ResponseEntity.ok(cityDtos);
    }

    @PostMapping("/update-city")
    public ResponseEntity<?> updateCity(@RequestParam Long cityId, @RequestParam String newName, @RequestParam Long newStateId) {
        locationService.updateCity(cityId, newName, newStateId);
        return ResponseEntity.ok("도시 정보가 업데이트 되었습니다.");
    }

    @PostMapping("/update-state")
    public ResponseEntity<?> updateState(@RequestParam Long stateId, @RequestParam String newName) {
        locationService.updateState(stateId, newName);
        return ResponseEntity.ok("주 정보가 업데이트 되었습니다.");
    }
}
