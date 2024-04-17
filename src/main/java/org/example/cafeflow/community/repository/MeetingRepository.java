package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByBoardId(Long boardId);
}
