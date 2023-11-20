package org.koreait.models.board.config;

import org.koreait.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class BoardNotAllowAccessException extends CommonException {
    public BoardNotAllowAccessException() {
        super("Validation.board.NotAllowAccess", HttpStatus.UNAUTHORIZED);
    }
}
