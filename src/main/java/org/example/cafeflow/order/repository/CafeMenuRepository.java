package org.example.cafeflow.order.repository;

import org.example.cafeflow.order.domain.CafeMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeMenuRepository extends JpaRepository<CafeMenu, Long> {
    List<CafeMenu> findByCafeId(Long cafeId);
}
