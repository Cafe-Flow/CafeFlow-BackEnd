package org.example.cafeflow.Member.repository;

import org.example.cafeflow.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 추가적인 사용자 정의 쿼리를 여기에 정의할 수 있습니다.
    // 예: 사용자 로그인ID로 회원 찾기
    Member findByLoginId(String loginId);
}
