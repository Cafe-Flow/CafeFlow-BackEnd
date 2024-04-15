package org.example.cafeflow.community.repository;

import org.example.cafeflow.community.domain.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {
    @Query("SELECT m FROM CommunityMember m LEFT JOIN FETCH m.friends WHERE m.id = :id")
    Optional<CommunityMember> findByIdWithFriends(@Param("id") Long id);
}