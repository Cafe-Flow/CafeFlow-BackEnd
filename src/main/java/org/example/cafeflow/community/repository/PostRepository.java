package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorId(Long authorId);
    List<Post> findByBoardId(Long boardId);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
        List<Post> searchByKeyword(@Param("keyword") String keyword);

    List<Post> findByTitleContaining(String title);

    @Query("SELECT p FROM Post p JOIN p.author a WHERE a.username = :username")
    List<Post> findByAuthorUsername(@Param("username") String username);

    List<Post> findByStateId(Long stateId);

    @Query("SELECT p FROM Post p WHERE p.state.name = :stateName")
    List<Post> findByStateName(@Param("stateName") String stateName);
}
