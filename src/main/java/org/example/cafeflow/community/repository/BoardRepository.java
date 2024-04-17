package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByName(String name);
    List<Board> findByPostsAuthorId(Long authorId);
}
