package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Column(length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberType mtype = MemberType.USER; // 기본값 일반회원

    /*@Lob
    private String termsAgree; // 약관 동의 내역

    @Transient // DB반영 X 내부에서만 사용
    private String tmpData;

    @ToString.Exclude // 출력 배제 -> 순환 참조 해결
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private List<BoardData> items = new ArrayList<>();*/

}
