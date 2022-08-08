package ksp.group3.miraiSugoroku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String showGuestSugorokuConfigPage() {
        return "guest_sugoroku_config";
    }

    @GetMapping("/sugoroku")
    public String showSugorokuPage() {
        return "sugoroku";
    }

}
