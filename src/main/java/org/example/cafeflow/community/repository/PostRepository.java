package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorId(Long authorId);
    List<Post> findByBoardId(Long boardId);
    @EntityGraph(attributePaths = {"author", "board", "comments", "likedBy"})
    @Query("SELECT p FROM Post p")
    List<Post> findAllPosts();

    @Query("SELECT p FROM Post p JOIN p.likedBy m WHERE m.id = :memberId")
    List<Post> findAllByLikedById(@Param("memberId") Long memberId);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
        List<Post> searchByKeyword(@Param("keyword") String keyword);

    List<Post> findByTitleContaining(String title);

    @Query("SELECT p FROM Post p JOIN p.author a WHERE a.nickname = :nickname")
    List<Post> findByAuthorNickname(@Param("nickname") String nickname);

    List<Post> findByStateId(Long stateId);

    @Query("SELECT p FROM Post p WHERE p.state.name = :stateName")
    List<Post> findByStateName(@Param("stateName") String stateName);
}
