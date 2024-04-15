package org.example.cafeflow.Member.controller;

import org.example.cafeflow.Member.domain.City;
import org.example.cafeflow.Member.domain.State;
import org.example.cafeflow.Member.dto.CityDto;
import org.example.cafeflow.Member.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/states")
    public ResponseEntity<List<State>> getAllStates() {
        List<State> states = locationService.getAllStates();
        return ResponseEntity.ok(states);
    }

    @GetMapping("/states/{stateId}/cities")
    public ResponseEntity<List<CityDto>> getCitiesByStateId(@PathVariable("stateId") Long stateId) {
        List<City> cities = locationService.getCitiesByStateId(stateId);
        List<CityDto> cityDtos = cities.stream()
                .map(city -> new CityDto(city.getId(), city.getName(), city.getState().getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(cityDtos);
    }

}
