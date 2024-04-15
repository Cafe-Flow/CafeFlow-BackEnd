package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
