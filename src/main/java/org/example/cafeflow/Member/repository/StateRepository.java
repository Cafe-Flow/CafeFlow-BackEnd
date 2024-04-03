package org.example.cafeflow.Member.repository;

import org.example.cafeflow.Member.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
    // 도 이름으로 주 찾기
    State findByName(String name);
}