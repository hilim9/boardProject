package org.koreait.commons.exceptions;

import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException {

    private HttpStatus status;

    public CommonException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR); // 기본값 500 에러
    }

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    
    public HttpStatus getStatus() { // 상태코드 조회
        return status;
    }
}
