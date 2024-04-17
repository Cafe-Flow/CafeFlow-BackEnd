package org.example.cafeflow.Member.repository;

import org.example.cafeflow.Member.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByName(String name);
}