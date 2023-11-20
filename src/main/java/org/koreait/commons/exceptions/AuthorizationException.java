package org.koreait.commons.exceptions;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends CommonException {

    public AuthorizationException(String message, HttpStatus status) {
        super("UnAuthorization", HttpStatus.UNAUTHORIZED);
    }

    public AuthorizationException(String code) {
        super(code, HttpStatus.UNAUTHORIZED);
    }
}
