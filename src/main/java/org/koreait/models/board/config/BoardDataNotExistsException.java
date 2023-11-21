package org.koreait.models.board.config;

public class BoardDataNotExistsException extends RuntimeException {

    public BoardDataNotExistsException() {
        super("파일을 찾을 수 없습니다");
    }
}
