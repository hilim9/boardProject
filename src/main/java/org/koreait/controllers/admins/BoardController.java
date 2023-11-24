package org.koreait.controllers.admins;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.ScriptExceptionProcess;
import org.koreait.commons.menus.Menu;
import org.koreait.controllers.boards.BoardForm;
import org.koreait.entities.Board;
import org.koreait.models.board.config.BoardConfigInfoService;
import org.koreait.models.board.config.BoardConfigListService;
import org.koreait.models.board.config.BoardConfigSaveService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller("adminBoardController")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController implements ScriptExceptionProcess {

    private final HttpServletRequest request;
    private final BoardConfigSaveService saveService;
    private final BoardConfigListService boardConfigListService;
    private final BoardConfigInfoService boardConfigInfoService;

    @GetMapping
    public String list(@ModelAttribute BoardSearch boardSearch, Model model) {
        commonProcess("list", model);

        Page<Board> data = boardConfigListService.gets(boardSearch);
        model.addAttribute("items", data.getContent());

        return "admin/board/list";
    }

    @GetMapping("/add")
    public String register(@ModelAttribute BoardConfigForm form, Model model) {
        commonProcess("add", model);

        return "admin/board/add";
    }

    @GetMapping("/update/{bId}")
    public String update(@PathVariable String bId, Model model) {
        commonProcess("update", model);

        Board board = boardConfigInfoService.get(bId, true);
        BoardConfigForm form = new ModelMapper().map(board, BoardConfigForm.class);
        form.setMode("update");

        model.addAttribute("form", form);

        return "admin/board/update";
    }

    @PostMapping("/save")
    public String save(@Valid BoardConfigForm form, Errors errors, Model model) {

        String mode = Objects.requireNonNullElse(form.getMode(), "update");
        commonProcess(mode, model);

        if (errors.hasErrors()) {
            return "admin/board/" + mode;
        }

        saveService.save(form);

        return "redirect:/admin/board";
    }
    
    private void commonProcess(String mode, Model model) {
        String pageTitle = "게시판 목록";
        mode = Objects.requireNonNullElse(mode, "list");
        if (mode.equals("add")) pageTitle = "게시판 등록";
        else if (mode.equals("update")) pageTitle = "게시판 수정";

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("menuCode", "board");
        model.addAttribute("submenus", Menu.gets("board"));
        model.addAttribute("subMenuCode", Menu.getSubMenuCode(request));
    }
}
