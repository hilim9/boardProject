package org.koreait.configs;

import org.koreait.models.member.MemberInfo;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        String email = null;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Object principal = auth.getPrincipal();
        // 비회원 일 때 - String(문자열로 출력) : anonymousUser
        // 회원 일 때 - UserDetails 구현 객체
        if (auth != null && auth.getPrincipal() instanceof MemberInfo) {
            MemberInfo memberInfo = (MemberInfo) auth.getPrincipal();
            email = memberInfo.getMember().getEmail();
        }

        return Optional.ofNullable(email);
    }
}
