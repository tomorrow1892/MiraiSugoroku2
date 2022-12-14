package ksp.group3.miraiSugoroku.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import ksp.group3.miraiSugoroku.service.SquareEventService;
import ksp.group3.miraiSugoroku.service.SquareService;
import ksp.group3.miraiSugoroku.entity.Square;
import ksp.group3.miraiSugoroku.form.GameConfigForm;

@Controller
public class UserController {
    @Autowired
    SquareService sService;
    @Autowired
    SquareEventService seService;

    @GetMapping("/")
    public String showIndexPage() {
        seService.registerSquareEvent();
        return "index";
    }

    @GetMapping("/menu")
    public String showGuestMenuPage() {
        return "guest_menu.html";
    }

    @GetMapping("/squares")
    public String showGuestSquareListPage(Model model) {
        List<Square> square_list = sService.filterSquaresByIsApproved(true);
        model.addAttribute("square_list", square_list);

        return "guest_squarelist";
    }

    // 検索結果の内承認済みのやつだけ表示できるように今後修正
    @GetMapping("/squares/search/keyword")
    public String searchSquaresByKeyword(@RequestParam("keyword") String keyword, Model model) {
        List<Square> square_list = sService.searchSquaresByKeyword(keyword);
        model.addAttribute("square_list", square_list);

        return "guest_squarelist";
    }

    // 検索結果の内承認済みのやつだけ表示できるように今後修正
    @GetMapping("/squares/search/nickname")
    public String searchSquaresByNickname(@RequestParam("nickname") String nickname, Model model) {
        List<Square> square_list = sService.searchSquaresByNickname(nickname);
        model.addAttribute("square_list", square_list);

        return "guest_squarelist";
    }

    @GetMapping("/config")
    public String showGuestSugorokuConfigPage(Model model) {
        model.addAttribute("GameConfigForm", new GameConfigForm());
        return "guest_sugoroku_config";
    }

    @PostMapping("/sugoroku/confirm")
    public String showSugorokuConfirm(GameConfigForm form, Model model) {
        form.addNameAndIcon();
        model.addAttribute("GameConfigForm", form);
        return "guest_sugoroku_confirm";
    }

    @PostMapping("/sugoroku")
    public String showSugorokuPage(GameConfigForm form, Model model) {
        model.addAttribute("GameConfigForm", form);
        return "sugoroku";
    }

}
