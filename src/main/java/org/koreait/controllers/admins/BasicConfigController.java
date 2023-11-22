package org.koreait.controllers.admins;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.Utils;
import org.koreait.commons.configs.ConfigInfoService;
import org.koreait.commons.configs.ConfigSaveService;
import org.koreait.controllers.admins.dtos.ConfigForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminBasicConfig")
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class BasicConfigController {
    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;

    private final String code = "config";

    @GetMapping
    public String config(Model model) {
        commonProcess(model);
        ConfigForm form = infoService.get(code, ConfigForm.class);

        model.addAttribute("configForm", form == null ? new ConfigForm() : form);
        return "admin/basic/index";
    }

    @PostMapping
    public String configPs(ConfigForm form, Model model) {
        commonProcess(model);

        saveService.save(code, form);

        model.addAttribute("message", Utils.getMessage("저장되었습니다","commons"));

        return "admin/basic/index";
    }

    private void commonProcess(Model model) {
        model.addAttribute("pageTitle", Utils.getMessage("사이트_설정","commons"));
        model.addAttribute("menuCode", code);
    }
}
