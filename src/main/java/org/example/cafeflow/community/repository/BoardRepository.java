package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // 이름으로 보드 조회
    Optional<Board> findByName(String name);
}