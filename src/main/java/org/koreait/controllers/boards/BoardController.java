package org.koreait.controllers.boards;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.MemberUtil;
import org.koreait.commons.ScriptExceptionProcess;
import org.koreait.commons.Utils;
import org.koreait.commons.exceptions.CommonException;
import org.koreait.entities.Board;
import org.koreait.entities.BoardData;
import org.koreait.entities.Member;
import org.koreait.models.board.BoardInfoService;
import org.koreait.models.board.BoardSaveService;
import org.koreait.models.board.config.BoardNotAllowAccessException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller("Controller2")
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController implements ScriptExceptionProcess {
    private final Utils utils;
    private final MemberUtil memberUtil;
    private final BoardSaveService saveService;
    private final BoardInfoService infoService;
    private final HttpServletResponse response;

    private Board board;

    @GetMapping("/write/{bId}")
    public String write(@PathVariable("bId") String bId, @ModelAttribute  BoardForm form, Model model) {
        commonProcess(bId, "write", model);

        return utils.tpl("board/write");
    }

    @GetMapping("/update/{seq}")
    public String update(@PathVariable("seq") Long seq, Model model) {

        BoardData boardData = infoService.get(seq, "update");
        board = boardData.getBoard();
        commonProcess(board.getBId(), "update", model);

        // 수정 권한 체크
        updateDeletePossibleCheck(boardData);

        BoardForm boardForm = new ModelMapper().map(boardData, BoardForm.class);
        if (boardData.getMember() == null) {
            board.setGuest(true);
        }

        model.addAttribute("boardForm", boardForm);

        return utils.tpl("board/update");
    }

    @PostMapping("/save")
    public String save(@Valid BoardForm form, Errors errors, Model model) {
        String mode = Objects.requireNonNullElse(form.getMode(), "write");
        String bId = form.getBId();

        commonProcess(bId, mode, model);

        if (errors.hasErrors()) {
            return utils.tpl("board/" + mode);
        }

        saveService.save(form);

        return "redirect:/board/list/" + bId;
    }

    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq, Model model) {

        BoardData data = infoService.get(seq);

        model.addAttribute("boardData", data);

        return utils.tpl("board/view");
    }

    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable Long seq) {

        return "redirect:/board/list/게시판 ID";
    }

    private void commonProcess(String bId, String mode, Model model) {
        String pageTitle = "게시글 목록";
        if (mode.equals("write")) pageTitle = "게시글 작성";
        else if (mode.equals("update")) pageTitle = "게시글 수정";
        else if (mode.equals("view")) pageTitle = "게시글 제목";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("write") || mode.equals("update")) {
            addCommonScript.add("ckeditor/ckeditor");
            addCommonScript.add("fileManager");

            addScript.add("board/form");
        }

        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("pageTitle", pageTitle);
    }

    /**
     * 수정, 삭제 권한 체크
     *
     * - 회원 : 작성한 회원
     * - 비회원 : 비밀번호 검증
     * - 관리자 : 가능
     *
     * @param boardData
     */
    public void updateDeletePossibleCheck(BoardData boardData, String mode) {
        mode = mode == null ? "board":mode;
        if (memberUtil.isAdmin()) { // 관리자는 무조건 가능
            return;
        }
        Member member = boardData.getMember();

        // 글을 작성한 회원쪽만 가능하게 통제
        if (memberUtil.isLogin()
                && Integer.parseInt(memberUtil.getMember().getEmail()) != boardData.getMember().getUserNo()) {
            throw new BoardNotAllowAccessException();
        }

    }

    public void updateDeletePossibleCheck(Long seq) {
        BoardData boardData = infoService.get(seq);
        updateDeletePossibleCheck(boardData, null);
    }

    public void updateDeletePossibleCheck(BoardData boardData) {
        updateDeletePossibleCheck(boardData, null);
    }

    @ExceptionHandler(CommonException.class)
    public String errorHandler(CommonException e, Model model) {
        e.printStackTrace();

        String message = e.getMessage();
        HttpStatus status = e.getStatus();
        response.setStatus(status.value());

        String script = String.format("alert('%s');history.back();", message);
        model.addAttribute("script", script);
        return "commons/execute_script";
    }
}
