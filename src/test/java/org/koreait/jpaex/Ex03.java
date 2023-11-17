package org.koreait.jpaex;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.koreait.entities.BoardData;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Transactional
@TestPropertySource(properties = "spring.profiles.active=test")
public class Ex03 {

    @PersistenceContext
    private EntityManager em;

    @Test
    void test1() {
        BoardData data = BoardData.builder()
                .subject("제목")
                .content("내용")
                .build();

        em.persist(data);
        em.flush();

        BoardData data2 = em.find(BoardData.class, data.getSeq());
        System.out.printf("1: %s%n2: %s%n",data2.getCreatedAt(), data2.getModifiedAt());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        data2.setSubject("(수정)제목");
        em.flush();

        data2 = em.find(BoardData.class, data.getSeq());
        System.out.printf("1: %s%n2: %s%n",data2.getCreatedAt(), data2.getModifiedAt());

    }
}
