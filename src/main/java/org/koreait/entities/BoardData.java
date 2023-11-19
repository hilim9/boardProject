package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.commons.constants.MemberType;

import java.util.ArrayList;
import java.util.List;

@Data @Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BoardData extends BaseMember {

    /*@Id @GeneratedValue
    private Long seq;

    @Column(length = 100, nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;*/

    @ManyToOne(fetch = FetchType.LAZY) // N : 1
    @JoinColumn(name = "userNo")
    private Member member;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<HashTag> tags = new ArrayList<>();

    @Id
    @GeneratedValue
    private Long bId; // 게시판 ID

    @Column(length=60, nullable=false)
    private String bName; // 게시판명

    @Column(name="isUse")
    private boolean use; // 사용 여부

    private int rowsOfPage = 20; // 1페이지당 게시글 수

    private boolean showViewList; // 게시글 하단 목록 노출

    @Lob
    private String category; // 게시판 분류

    // 목록 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private MemberType listAccessRole = MemberType.ALL;

    // 글보기 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private MemberType ViewAccessRole = MemberType.ALL;

    // 글쓰기 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private MemberType writeAccessRole = MemberType.ALL;

    // 답글 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private MemberType replyAccessRole = MemberType.ALL;

    // 댓글 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private MemberType commentAccessRole = MemberType.ALL;

    // 에디터 사용 여부
    private boolean useEditor;

    // 파일 첨부 사용여부
    private boolean useAttachFile;

    // 이미지 첨부 사용여부
    private boolean useAttachImage;

    // 글작성 후 이동
    @Column(length=10, nullable=false)
    private String locationAfterWriting = "view";

    // 답글 사용 여부
    private boolean useReply;

    // 댓글 사용 여부
    private boolean useComment;

    // 게시판 스킨
    @Column(length=20, nullable=false)
    private String skin = "default";

    /**
     * 게시판 분류 목록
     *
     * @return
     */
    public String[] getCategories() {
        if (category == null) {
            return null;
        }
        String[] categories = category.replaceAll("\\r", "").trim().split("\\n");
        return categories;
    }

}
