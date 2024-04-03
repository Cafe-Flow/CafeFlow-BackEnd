package org.example.cafeflow.Member.controller;

import org.example.cafeflow.Member.domain.City;
import org.example.cafeflow.Member.domain.State;
import org.example.cafeflow.Member.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<City>> getCitiesByStateId(@PathVariable Long stateId) {
        List<City> cities = locationService.getCitiesByStateId(stateId);
        return ResponseEntity.ok(cities);
    }
}
