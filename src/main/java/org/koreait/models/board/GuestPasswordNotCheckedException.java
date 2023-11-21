package org.koreait.models.board;

import org.koreait.commons.Utils;
import org.koreait.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 비회원 비밀번호 미검증시 발생하는 예외
 */
public class GuestPasswordNotCheckedException extends CommonException {
    public GuestPasswordNotCheckedException() {
        super(Utils.getMessage("GuestPw.notChecked", "validations"), HttpStatus.UNAUTHORIZED);
    }
}
