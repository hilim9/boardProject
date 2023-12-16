package org.koreait.controllers.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("frontIndexController")
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String index() {
        return "front/main/index";
}
