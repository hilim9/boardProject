package org.koreait.models.board;

import org.koreait.commons.Utils;
import org.koreait.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 게시판 유효성 검사 관련 예외
 *
 */
public class BoardValidationException extends CommonException {
    public BoardValidationException(String code) {
        super(Utils.getMessage(code,"validations"), HttpStatus.BAD_REQUEST);
    }
}
