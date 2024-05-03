package org.example.cafeflow.Member.util;

import lombok.Getter;
import org.example.cafeflow.Member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String nickname;

    // 생성자 추가
    public UserPrincipal(Member member) {
        this.id = member.getId();
        this.username = member.getLoginId();
        this.password = member.getPasswordHash();
        this.nickname = member.getNickname();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 정보를 반환해야 하지만 현재는 빈 Collection을 반환합니다.
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 반환 (여기서는 항상 true로 설정)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 반환 (여기서는 항상 true로 설정)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부 반환 (여기서는 항상 true로 설정)
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부 반환 (여기서는 항상 true로 설정)
    }
}
