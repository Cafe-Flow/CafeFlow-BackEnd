package org.example.cafeflow.Member.util;

import lombok.Getter;
import org.example.cafeflow.Member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String nickname;
    private final Member.UserType userType;

    // 생성자 추가
    public UserPrincipal(Member member) {
        this.id = member.getId();
        this.username = member.getLoginId();
        this.password = member.getPasswordHash();
        this.nickname = member.getNickname();
        this.userType = member.getUserType();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // UserType에 따라 다른 권한 부여
        if (userType == Member.UserType.ADMIN) {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        }
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