package org.example.cafeflow.order.repository;

import org.example.cafeflow.order.domain.BasicMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasicMenuRepository extends JpaRepository<BasicMenu, Long> {
    List<BasicMenu> findByNameContaining(String name);
}
