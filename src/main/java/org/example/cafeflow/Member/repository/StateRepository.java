package org.example.cafeflow.Member.repository;

import org.example.cafeflow.Member.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
