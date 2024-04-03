package org.example.cafeflow.Member.service;

import org.example.cafeflow.Member.domain.City;
import org.example.cafeflow.Member.domain.State;
import org.example.cafeflow.Member.repository.CityRepository;
import org.example.cafeflow.Member.repository.StateRepository;
import org.example.cafeflow.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    public List<City> getCitiesByStateId(Long stateId) {
        if (!stateRepository.existsById(stateId)) {
            throw new ResourceNotFoundException("State not found for this id :: " + stateId);
        }
        return cityRepository.findByStateId(stateId);
    }
}
