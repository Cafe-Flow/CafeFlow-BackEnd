package org.example.cafeflow.Member.service;

import org.example.cafeflow.Member.domain.City;
import org.example.cafeflow.Member.domain.State;
import org.example.cafeflow.Member.dto.CityDto;
import org.example.cafeflow.Member.dto.StateDto;
import org.example.cafeflow.Member.repository.CityRepository;
import org.example.cafeflow.Member.repository.StateRepository;
import org.example.cafeflow.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CityRepository cityRepository;

    public List<StateDto> getAllStates() {
        return stateRepository.findAll().stream()
                .map(state -> new StateDto(state.getId(), state.getName()))
                .collect(Collectors.toList());
    }

    public List<CityDto> getCitiesByStateId(Long stateId) {
        if (!stateRepository.existsById(stateId)) {
            throw new ResourceNotFoundException("해당 ID를 가진 '도/시'를 찾을 수 없습니다: " + stateId);
        }
        return cityRepository.findByStateId(stateId).stream()
                .map(city -> new CityDto(city.getId(), city.getName(), city.getState().getId()))
                .collect(Collectors.toList());
    }

    public void updateCity(Long cityId, String newName, Long newStateId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID를 가진 '시'를 찾을 수 없습니다: " + cityId));
        State state = stateRepository.findById(newStateId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID를 가진 '도'를 찾을 수 없습니다: " + newStateId));
        city.setName(newName);
        city.setState(state);
        cityRepository.save(city);
    }

    public void updateState(Long stateId, String newName) {
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID를 가진 '주'를 찾을 수 없습니다: " + stateId));
        state.setName(newName);
        stateRepository.save(state);
    }

    public CityDto getCityByNameAndStateId(String name, Long stateId) {
        return cityRepository.findByNameAndStateId(name, stateId)
                .map(city -> new CityDto(city.getId(), city.getName(), city.getState().getId()))
                .orElseThrow(() -> new ResourceNotFoundException("해당 이름과 ID를 가진 도시를 찾을 수 없습니다."));
    }

    public StateDto getStateByName(String name) {
        return stateRepository.findByName(name)
                .map(state -> new StateDto(state.getId(), state.getName()))
                .orElseThrow(() -> new ResourceNotFoundException("해당 이름을 가진 주를 찾을 수 없습니다."));
    }
}
