package org.koreait.models.board.config;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.MemberUtil;
import org.koreait.commons.constants.MemberType;
import org.koreait.entities.BoardData;
import org.koreait.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardConfigInfoService {

    private final BoardDataRepository boardRepository;
    private final MemberUtil memberUtil;

    public BoardData get(Long bId, String location) { // 프론트, 접근 권한 체크
        return get(bId, false, location);
    }

    /**
     * 게시판 설정 조회
     *
     * @param bId
     * @param isAdmin : true - 권한 체크 X
     *                : false - 권한 체크, location으로 목록, 보기, 글쓰기, 답글, 댓글
     *
     * @param location : 기능 위치(list, view, write, reply, comment)
     *
     * @return
     */
    public BoardData get(Long bId, boolean isAdmin, String location) {

        BoardData board = boardRepository.findById(bId).orElseThrow(BoardConfigNotExistException::new);

        if (!isAdmin) { // 권한 체크
            accessCheck(board, location);
        }

        return board;
    }

    public BoardData get(Long bId, boolean isAdmin) {
        return get(bId, isAdmin, null);
    }

    /**
     * 접근 권한 체크
     *
     * @param board
     */
    private void accessCheck(BoardData board, String location) {
        MemberType memberType = MemberType.ALL;
        if (location.equals("list")) { // 목록 접근 권한
            memberType = board.getListAccessRole();

        } else if (location.equals("view")) { // 게시글 접근 권한
            memberType = board.getViewAccessRole();

        } else if (location.equals("write")) { // 글쓰기 권한
            memberType = board.getWriteAccessRole();

        } else if (location.equals("reply")) { // 답글 권한
            memberType = board.getReplyAccessRole();

        } else if (location.equals("comment")) { // 댓글 권한
            memberType = board.getCommentAccessRole();

        }

        if ((memberType == MemberType.USER && !memberUtil.isLogin())
                || (memberType == MemberType.ADMIN && !memberUtil.isAdmin())) {
            throw new BoardNotAllowAccessException();
        }
    }
}
