package org.koreait.models.board.config;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.ListData;
import org.koreait.commons.Pagination;
import org.koreait.controllers.admins.BoardSearch;
import org.koreait.entities.Board;
import org.koreait.entities.QBoard;
import org.koreait.repositories.BoardRepository;
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

    private final BoardRepository boardRepository;

    private final HttpServletRequest request;

    public ListData<Board> gets(BoardSearch boardSearch) {
        QBoard board = QBoard.board;

        BooleanBuilder andBuilder = new BooleanBuilder();

        int page = boardSearch.getPage();
        int limit = boardSearch.getLimit();
        page = page < 1 ? 1 : page;
        limit = limit < 1 ? 20 : limit;

        /** 검색 조건 처리 S */
        String sopt = boardSearch.getSopt();
        String skey = boardSearch.getSkey();
        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            skey = skey.trim();
            sopt = sopt.trim();

            if (sopt.equals("all")) { // 통합 검색 - bId, bName
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(board.bId.contains(skey))
                        .or(board.bName.contains(skey));
                andBuilder.and(orBuilder);

            } else if (sopt.equals("bId")) { // 게시판 아이디 bId
                andBuilder.and(board.bId.contains(skey));
            } else if (sopt.equals("bName")) { // 게시판명 bName
                andBuilder.and(board.bName.contains(skey));
            }
        }
        /** 검색 조건 처리 E
         *
         * 등록순으로 내림차순
         * Sort.by(desc("createdAt"))
         * */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
        Page<Board> data = boardRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        ListData<Board> listData = new ListData<>();
        listData.setContent(data.getContent());
        listData.setPagination(pagination);

        return listData;
    }
}
