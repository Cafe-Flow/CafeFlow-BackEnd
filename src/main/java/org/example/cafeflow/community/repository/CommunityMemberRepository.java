package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {
    // Fetch를 사용하여 friends 관계를 로드
    @Query("SELECT m FROM CommunityMember m LEFT JOIN FETCH m.friends WHERE m.id = :id")
    Optional<CommunityMember> findByIdWithFriends(@Param("id") Long id);

    // 다른 쿼리 메서드들
    Optional<CommunityMember> findByUsername(String username);
}
