package org.koreait.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor // 초기화 할 수 있는 생성자 추가
public class BoardViewId {

    private Long seq;
    private Integer uid;
}
