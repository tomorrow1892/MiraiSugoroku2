package ksp.group3.miraiSugoroku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ksp.group3.miraiSugoroku.form.GameConfigForm;

@Controller
public class UserController {
    // @Autowired
    // SquareRepository sRepo;

    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/menu")
    public String showGuestMenuPage() {
        return "guest_menu.html";
    }

    @GetMapping("/squares")
    public String showGuestSquareListPage() {
        return "guest_squarelist";
    }

    @GetMapping("/config")
    public String showGuestSugorokuConfigPage(Model model) {
        model.addAttribute("GameConfigForm", new GameConfigForm());
        return "guest_sugoroku_config";
    }

    @PostMapping("/sugoroku/confirm")
    public String showSugorokuConfirm(GameConfigForm form, Model model) {
        model.addAttribute("GameConfigForm", form);
        return "guest_sugoroku_confirm";
    }

    @PostMapping("/sugoroku")
    public String showSugorokuPage(GameConfigForm form, Model model) {
        model.addAttribute("GameConfigForm", form);
        return "sugoroku";
    }

}
