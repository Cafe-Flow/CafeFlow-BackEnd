package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 특정 보드의 게시물을 조회
    @Query("SELECT p FROM Post p WHERE p.board.id = :boardId")
    List<Post> findAllByBoardId(@Param("boardId") Long boardId);
}