package org.koreait.models.board.config;

import org.koreait.commons.Utils;
import org.koreait.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class BoardNotAllowAccessException extends CommonException {
    public BoardNotAllowAccessException() {
        super(Utils.getMessage("Board.NotAllowAccess", "validations"), HttpStatus.UNAUTHORIZED);
    }
}
