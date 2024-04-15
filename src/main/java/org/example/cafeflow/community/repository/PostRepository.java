package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorId(Long authorId);
    List<Post> findAllByBoardId(Long boardId);

    @Query("SELECT p FROM Post p WHERE p.content LIKE %:keyword%")
    List<Post> searchByContent(@Param("keyword") String keyword);
}