package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    List<Comment> findByAuthorId(Long authorId);

    @Query("SELECT c FROM Comment c JOIN c.author a WHERE a.nickname = :nickname")
    List<Comment> findByAuthorNickname(@Param("nickname") String nickname);
}
