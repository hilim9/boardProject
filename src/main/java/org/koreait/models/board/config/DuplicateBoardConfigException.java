package org.koreait.models.board.config;

import org.koreait.commons.Utils;
import org.koreait.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class DuplicateBoardConfigException extends CommonException {

    public DuplicateBoardConfigException() {
        super(Utils.getMessage("Board.Exists", "validations"), HttpStatus.BAD_REQUEST);
    }
}
