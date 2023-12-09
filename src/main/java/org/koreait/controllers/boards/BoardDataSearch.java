package org.koreait.controllers.boards;

import lombok.Data;

@Data
public class BoardDataSearch {

    private String bId;
    private int page = 1; // 기본값 1페이지
    private int limit = 20; // 한 페이지에 20개
    private String sopt;
    private String skey;

    // 제목, 제목 + 내용, 작성자명, 작성자 + 아이디

}
