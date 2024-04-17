package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorId(Long authorId);
    List<Post> findAllByBoardId(Long boardId);
    List<Post> searchByContent(String keyword);
    List<Post> findByBoardName(String boardName);

    List<Post> findByBoardIdAndRegion(Long boardId, String region);
}