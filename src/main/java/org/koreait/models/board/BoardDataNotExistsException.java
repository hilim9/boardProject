package org.koreait.models.board;

import org.koreait.commons.Utils;
import org.koreait.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class BoardDataNotExistsException extends CommonException {

    public BoardDataNotExistsException() {

        super(Utils.getMessage("Board.notExists", "validations"), HttpStatus.BAD_REQUEST);
    }
}
