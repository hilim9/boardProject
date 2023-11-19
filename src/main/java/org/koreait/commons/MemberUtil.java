package org.koreait.commons;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.constants.MemberType;
import org.koreait.entities.Member;
import org.koreait.models.member.MemberInfo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;

    public boolean isLogin() { // 로그인 여부 조회

        return getMember() != null; // true면 로그인 false면 로그인X
    }

    /*public Member getMember() { // 메모리 할당하지 않고 바로 반환

        return (Member) session.getAttribute("loginMember");
    }*/

    /**
     * 현재 사용자가 관리자 권한을 가지고 있는지 확인.
     *
     * @return 관리자 권한이 있는 경우 true, 아닌 경우 false를 반환.
     */
    public boolean isAdmin() {
        return isLogin() && getMember().getMemberType() == MemberType.ADMIN;
    }

    /**
     * 현재 로그인 중인 회원 정보를 가져옴.
     *
     * @return 현재 로그인 중인 회원 정보 객체를 반환.
     */
    public MemberInfo getMember() {

        return (MemberInfo) session.getAttribute("loginMember");
    }

    /**
     * 현재 로그인 중인 회원 정보를 엔티티 객체로 변환하여 반환.
     *
     * @return 로그인 중인 회원 정보의 엔티티 객체를 반환합니다. 로그인 중이 아닌 경우에는 null을 반환.
     */
    public Member getEntity() {
        if (isLogin()) {
            Member member = new ModelMapper().map(getMember(), Member.class);
            return member;
        }

        return null;
    }

}
