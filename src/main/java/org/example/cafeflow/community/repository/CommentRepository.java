package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 포스트에 대한 댓글 조회
    List<Comment> findByPostId(Long postId);
}