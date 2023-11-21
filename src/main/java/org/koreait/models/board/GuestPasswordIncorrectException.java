package org.koreait.models.board;

import org.koreait.commons.Utils;
import org.koreait.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class GuestPasswordIncorrectException extends CommonException {
    public GuestPasswordIncorrectException() {
        super(Utils.getMessage("GuestPw.incorrect", "validations"), HttpStatus.BAD_REQUEST);
    }
}
