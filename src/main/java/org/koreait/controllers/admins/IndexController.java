package org.koreait.controllers.admins;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminIndexController") // 클래스 충돌 방지 (이름 변경)
@RequestMapping("/admin")
public class IndexController {
    @GetMapping
    public String index() {
        return "admin/main/index";
    }
}
