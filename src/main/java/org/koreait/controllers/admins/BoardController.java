package org.koreait.controllers.admins;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.ScriptExceptionProcess;
import org.koreait.commons.exceptions.CommonException;
import org.koreait.commons.menus.Menu;
import org.koreait.commons.menus.MenuDetail;
import org.koreait.entities.Board;
import org.koreait.models.board.config.BoardConfigInfoService;
import org.koreait.models.board.config.BoardConfigListService;
import org.koreait.models.board.config.BoardConfigSaveService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller("adminBoardController")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController implements ScriptExceptionProcess {

    private final BoardConfigSaveService configSaveService;
    private final BoardConfigInfoService boardConfigInfoService;
    private final BoardConfigListService boardConfigListService;
    private final HttpServletRequest request;
    
    // 게시판 목록
    @GetMapping
    public String index(Model model) {
        commonProcess(model, "list");
        return "admin/board/index";
    }
    
    // 게시판 등록
    @GetMapping("/register") // add
    public String register(@ModelAttribute BoardForm boardForm, Model model) {
        commonProcess(model, "게시판 등록");

        return "admin/board/config";
    }
    
    // 게시판 수정
    @GetMapping("/update/{bId}")
    public String update(@PathVariable String bId, Model model) {
        commonProcess(model, "게시판 수정");

        Board board = boardConfigInfoService.get(bId, true);
        BoardForm boardForm = new ModelMapper().map(board, BoardForm.class);
        boardForm.setMode("update");
        boardForm.setListAccessRole(board.getListAccessRole().toString());
        boardForm.setViewAccessRole(board.getViewAccessRole().toString());
        boardForm.setWriteAccessRole(board.getWriteAccessRole().toString());
        boardForm.setReplyAccessRole(board.getReplyAccessRole().toString());
        boardForm.setCommentAccessRole(board.getCommentAccessRole().toString());

        model.addAttribute("boardForm", boardForm);

        return "admin/board/config";
    }
    
    // 게시글 저장
    @PostMapping("/save")
    public String save(@Valid BoardForm boardForm, Errors errors, Model model) {
        String mode = boardForm.getMode();
        commonProcess(model, mode != null && mode.equals("update") ? "게시판 수정" : "게시판 등록");

        try {
            configSaveService.save(boardForm, errors);
        } catch (CommonException e) {
            errors.reject("BoardConfigError", e.getMessage());
        }

        if (errors.hasErrors()) {
            return "admin/board/config";
        }


        return "redirect:/admin/board"; // 게시판 목록
    }

    private void commonProcess(Model model, String mode) {
        mode = Objects.requireNonNullElse(mode, "list");

        String pageTitle = "게시판 목록";

        if (mode.equals("register")) {
            pageTitle = "게시판 등록";
        } else if (mode.equals("update")) {
            pageTitle = "게시글 수정";
        } else if (mode.equals("posts")) {
            pageTitle = "게시글 관리";
        }

        model.addAttribute("menuCode", "board");

        // 서브 메뉴 처리
        String subMenuCode = Menu.getSubMenuCode(request);
        subMenuCode = mode.equals("update") ? "register" : subMenuCode;
        model.addAttribute("subMenuCode", subMenuCode);

        List<MenuDetail> submenus = Menu.gets("board");
        model.addAttribute("submenus", submenus);

        model.addAttribute("pageTitle", pageTitle);
    }
}
