package org.koreait.models.board.config;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.constants.MemberType;
import org.koreait.controllers.admin.BoardForm;
import org.koreait.entities.BoardData;
import org.koreait.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

/**
 * 게시판 설정 추가, 수정
 *
 */
@Service
@RequiredArgsConstructor
public class BoardConfigSaveService {

    private final BoardDataRepository boardRepository;

    public void save(BoardForm boardForm) {
        save(boardForm, null);
    }

    public void save(BoardForm boardForm, Errors errors) {

        if (errors != null && errors.hasErrors()) {
            return;
        }

        /**
         * 게시판 설정 조회 -> 없으면 엔티티 생성
         * 게시판 등록 모드인 경우는 중복 여부 체크
         *
         */
        Long bId = boardForm.getBId();
        BoardData board = boardRepository.findById(bId).orElseGet(BoardData::new);
        String mode = boardForm.getMode();
        if ((mode == null || !mode.equals("update")) && board.getBId() != null) { // 게시판 등록 -> 중복 여부 체크
            throw new DuplicateBoardConfigException();
        }

        board.setBId(bId);
        board.setBName(boardForm.getBName());
        board.setUse(boardForm.isUse());
        board.setRowsOfPage(boardForm.getRowsOfPage());
        board.setShowViewList(boardForm.isShowViewList());
        board.setCategory(boardForm.getCategory());
        board.setListAccessRole(MemberType.valueOf(boardForm.getListAccessRole()));

        board.setViewAccessRole(MemberType.valueOf(boardForm.getViewAccessRole()));

        board.setWriteAccessRole(MemberType.valueOf(boardForm.getWriteAccessRole()));

        board.setReplyAccessRole(MemberType.valueOf(boardForm.getReplyAccessRole()));

        board.setCommentAccessRole(MemberType.valueOf(boardForm.getCommentAccessRole()));

        board.setUseEditor(boardForm.isUseEditor());
        board.setUseAttachFile(boardForm.isUseAttachFile());
        board.setUseAttachImage(boardForm.isUseAttachImage());

        board.setLocationAfterWriting(boardForm.getLocationAfterWriting());

        board.setUseReply(boardForm.isUseReply());
        board.setUseComment(boardForm.isUseComment());
        board.setSkin(boardForm.getSkin());
        System.out.println(board);
        boardRepository.saveAndFlush(board);
    }
}
