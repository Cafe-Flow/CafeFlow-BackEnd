package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByUserId(Long userId);
}
