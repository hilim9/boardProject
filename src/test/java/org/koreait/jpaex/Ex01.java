package org.koreait.jpaex;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.commons.constants.MemberType;
import org.koreait.entities.Member;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional // 트랜잭션 수동 관리 (Commit)
public class Ex01 {

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void init() {
        Member member = new Member();
        //member.setUserNo(1L); 시퀀스 설정
        member.setEmail("user01@test.org");
        member.setUserNm("사용자01");
        member.setPassword("123456");
        member.setMobile("01012345678");
        member.setMtype(MemberType.USER);

        em.persist(member); // 변화 감지 상태
        em.flush(); // DB 반영
        em.clear(); // 영속성 비우기. 변화, 감지 하지 않음

        /*em.detach(member); // 영속성 분리, 변화 감지 X

        member.setUserNm("(수정) 사용자01");
        em.flush();

        em.merge(member); // 분리된 영속 상태 -> 영속 상태, 변화 감지 O
        em.flush();*/

        //em.remove(member);

    }

    @Test
    void test2() {
        
        Member member = em.find(Member.class,1L); // DB -> 영속성 컨텍스트로 추가
        System.out.println(member);
        
        Member member2 = em.find(Member.class, 1L); // 영속성 컨텍스트안에서 조회
        System.out.println(member2);

        TypedQuery<Member> query = em.createQuery("SELECT m FROM Member AS m WHERE m.email LIKE : key", Member.class); // m -> *
        query.setParameter("key", "%user%");
        Member member3 = query.getSingleResult();

        member3.setUserNm("(수정)사용자01");
        em.flush();

    }

    @Test
    void test1() {
        Member member = new Member();
        //member.setUserNo(1L);
        member.setEmail("user01@test.org");
        member.setUserNm("사용자01");
        member.setPassword("123456");
        member.setMobile("01012345678");
        member.setMtype(MemberType.USER);

        em.persist(member); // 변화 감지 상태
        em.flush();
        em.clear(); // 영속성 비우기 변화, 감지 하지 않음

        em.detach(member); // 영속성 분리, 변화 감지 X

        member.setUserNm("(수정) 사용자01");
        em.flush();

        em.merge(member); // 분리된 영속 상태 -> 영속 상태, 변화 감지 O
        em.flush();

        //em.remove(member);

    }

    @Test
    void test3() {

    }
}
