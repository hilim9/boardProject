package org.koreait.commons.rest;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class JSONData<T> {

    private boolean success = true;
    private HttpStatus status = HttpStatus.OK;

    @NonNull
    private T data; // 성공시 들어갈 데이터
    private String message; // 실패시 보여줄 메시지

}
