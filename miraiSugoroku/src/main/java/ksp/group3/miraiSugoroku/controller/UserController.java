package ksp.group3.miraiSugoroku.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ksp.group3.miraiSugoroku.service.SquareService;
import ksp.group3.miraiSugoroku.entity.Square;

@Controller
public class UserController {
    @Autowired
    SquareService sService;

    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/menu")
    public String showGuestMenuPage() {
        return "guest_menu.html";
    }

    @GetMapping("/squares")
    public String showGuestSquareListPage(Model model) {
        List<Square> square_list = sService.getAllSquare();
        model.addAttribute("square_list", square_list);
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
