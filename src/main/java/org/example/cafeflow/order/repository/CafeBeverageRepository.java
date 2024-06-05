package org.example.cafeflow.order.repository;

import org.example.cafeflow.order.domain.CafeBeverage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeBeverageRepository extends JpaRepository<CafeBeverage, Long> {
    List<CafeBeverage> findByCafeId(Long cafeId);
}
