package ksp.group3.miraiSugoroku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreatorController {
    // @Autowired
    // EventService eService;
    // @Autowired
    // CreatorService cService;
    // @Autowired
    // SquareService sService;

    @GetMapping("/loginpage")
    public String showcreatorLoginPage() {
        return "creator_login";
    }

    @GetMapping("/login")
    public String creatorLogin(Long eventId, String loginId) {
        String cid = "test";
        return "redirect:/" + cid + "/manu";
    }

    @GetMapping("/{cid}/manu")
    public String showcreatorMenu() {
        return "creator_menu";
    }

    @GetMapping("/{cid}/detail")
    public String showcreatorDetailForm() {
        return "creator_profile";
    }

    @PostMapping("/{cid}/update")
    public String updatecreatorDetail() {// (SquarecreatorForm form) {
        String cid = "test";
        return "redirect:/" + cid + "/menu";
    }

    @GetMapping("/{cid}/squares")
    public String showSquare() {
        return "creator_squarelist";
    }

    @GetMapping("/{cid}/create")
    public String showSquareCreateFrom() {
        return "creator_create";
    }

    @PostMapping("/{cid}/create/confirm")
    public String showSquareCreateConfirm() {// (SquareFrom form) {
        return "creator_create_confirm";
    }

    @PostMapping("/{cid}/create/apply")
    public String showSquareCreateDonePage() {// (SquareFrom form) {
        return "creator_create_done";
    }

    @GetMapping("/{cid}/config")
    public String showSugorokuManu() {
        return "creator_sugoroku_config";
    }

}
