package org.koreait.entities;

import jakarta.persistence.*;
import lombok.*;
import org.koreait.commons.constants.MemberType;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "idx_member_userNm", columnList = "userNm"),
        @Index(name = "idx_member_mobile", columnList = "mobile")
})
public class Member extends Base {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userNo;

    @Column(unique = true, nullable = false, length = 65)
    private String email;

    @Column(name = "pw", nullable = false, length = 65)
    private String password;

    @Column(nullable = false, length = 40)
    private String userNm;

    @Column(length = 11)
    private String mobile;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberType mtype = MemberType.USER; // 기본값 일반회원

}
