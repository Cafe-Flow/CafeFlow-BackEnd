package org.example.cafeflow.Member.repository;

import org.example.cafeflow.Member.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    // 도시 이름으로 도시 찾기
    City findByName(String name);
    List<City> findByStateId(Long stateId);

}