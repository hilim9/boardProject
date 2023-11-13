package org.koreait.models.member;

import lombok.Builder;
import lombok.Data;
import org.koreait.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data @Builder
public class MemberInfo implements UserDetails {

    private String email;
    private String password;

    private Member member;
    private Collection<? extends GrantedAuthority> authorities;


    /* 필수 설정 항목 S */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 권한 (인가 통제)
        return authorities;
    }

    @Override
    public String getPassword() { // 로그인 관련
        return password;
    }

    @Override
    public String getUsername() { // 로그인 관련
        return email;
    }

    /* 필수 설정 항목 E */

    @Override
    public boolean isAccountNonExpired() { 
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비번 사용기한 관련
        return true;
    }

    @Override
    public boolean isEnabled() { // 회원 존재 여부
        return true;
    }
}
