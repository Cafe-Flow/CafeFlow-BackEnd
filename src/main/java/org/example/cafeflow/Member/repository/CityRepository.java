package org.example.cafeflow.Member.repository;

import org.example.cafeflow.Member.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByStateId(Long stateId);

}
