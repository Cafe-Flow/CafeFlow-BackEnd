package org.example.cafeflow.order.repository;

import org.example.cafeflow.order.domain.BasicBeverage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasicBeverageRepository extends JpaRepository<BasicBeverage, Long> {
    List<BasicBeverage> findByNameContaining(String name);
}
