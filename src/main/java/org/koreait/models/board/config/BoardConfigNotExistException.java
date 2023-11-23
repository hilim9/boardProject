package org.koreait.models.board.config;

import org.koreait.commons.Utils;
import org.koreait.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class BoardConfigNotExistException extends CommonException {
    public BoardConfigNotExistException() {
        super(Utils.getMessage("Board.notExists","validations"), HttpStatus.NOT_FOUND);
    }
}
