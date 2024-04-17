package org.example.cafeflow.Member.repository;

import org.example.cafeflow.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
    List<Member> findByNicknameContaining(String nickname);
}
