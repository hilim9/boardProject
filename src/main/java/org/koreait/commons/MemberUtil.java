package org.koreait.commons;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.koreait.entities.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;

    public boolean isLogin() { // 로그인 여부 조회
        return getMember() != null; // true면 로그인 false면 로그인X
    }

    public Member getMember() { // 메모리 할당하지 않고 바로 반환
        return (Member) session.getAttribute("loginMember");
    }

}
