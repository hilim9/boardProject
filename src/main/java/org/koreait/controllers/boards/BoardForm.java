package org.koreait.controllers.boards;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardForm {
    private Long seq; // 게시글 번호

    @NotBlank
    private String bId;

    @NotBlank
    private String gid = UUID.randomUUID().toString();

    @NotBlank(message = "작성자를 입력하세요")
    private String poster; // 작성자
    private String guestPw; // 비회원 비밀번호
    private String category; // 게시판 분류

    @NotBlank(message = "제목을 입력하세요")
    private String subject; // 제목

    @NotBlank(message = "내용을 입력하세요")
    private String content; // 내용

    private Long userNo; // 회원번호
}
