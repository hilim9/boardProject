package org.koreait.models.board.config;

import org.koreait.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class BoardConfigNotExistException extends CommonException {
    public BoardConfigNotExistException() {
        super("Validation.board.notExists", HttpStatus.BAD_REQUEST);
    }
}
