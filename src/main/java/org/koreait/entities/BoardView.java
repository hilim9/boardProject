package org.koreait.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Entity
@Data
@IdClass(BoardViewId.class)
public class BoardView {

    @Id
    private Long seq; // 게시글 번호

    @Id
    private Integer uid; // viewUid

}
