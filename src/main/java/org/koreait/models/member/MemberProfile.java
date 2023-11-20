package org.koreait.models.member;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.koreait.entities.Member;

@Entity @Data
public class MemberProfile {

    @Id @GeneratedValue
    private Long seq;

    @Column(length = 100)
    private String image;

    @ToString.Exclude
    @OneToOne(mappedBy = "profile") // 관계 주인 명시
    private Member member;

}
