package org.koreait.models.board.config;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.koreait.controllers.admin.BoardSearch;
import org.koreait.entities.BoardData;
import org.koreait.entities.QBoardData;
import org.koreait.repositories.BoardDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Order.desc;

/**
 * 게시판 설정 목록
 *
 */
@Service
@RequiredArgsConstructor
public class BoardConfigListService {

    private final BoardDataRepository boardDataRepository;

    public Page<BoardData> gets(BoardSearch boardSearch) {
        QBoardData boardData = QBoardData.boardData;

        BooleanBuilder andBuilder = new BooleanBuilder();

        int page = boardSearch.getPage();
        int limit = boardSearch.getLimit();
        page = page < 1 ? 1 : page;
        limit = limit < 1 ? 20 : limit;

        /** 검색 조건 처리 S */
        String sopt = boardSearch.getSopt();
        String skey = boardSearch.getSkey();
        String bId = boardData.bId.toString();
        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            skey = skey.trim();
            sopt = sopt.trim();

            if (sopt.equals("all")) { // 통합 검색 - bId, bName
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(boardData.bId.like(skey)) // contain 변경
                        .or(boardData.bName.contains(skey));
                andBuilder.and(orBuilder);

            } else if (sopt.equals("bId")) { // 게시판 아이디 bId
                andBuilder.and(boardData.bId.like(skey)); // contain 변경
            } else if (sopt.equals("bName")) { // 게시판명 bName
                andBuilder.and(boardData.bName.contains(skey));
            }
        }
        /** 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
        Page<BoardData> data = boardDataRepository.findAll(andBuilder, pageable);

        return data;
    }
}
