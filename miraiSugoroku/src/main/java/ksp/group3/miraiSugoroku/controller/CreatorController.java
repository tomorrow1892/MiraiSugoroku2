package ksp.group3.miraiSugoroku.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ksp.group3.miraiSugoroku.entity.Square;
import ksp.group3.miraiSugoroku.entity.SquareEvent;
import ksp.group3.miraiSugoroku.form.SquareForm;
import ksp.group3.miraiSugoroku.service.CreatorService;
import ksp.group3.miraiSugoroku.service.EventService;
import ksp.group3.miraiSugoroku.service.SquareService;

@Controller
public class CreatorController {
    @Autowired
    EventService eService;
    @Autowired
    CreatorService cService;
    @Autowired
    SquareService sService;

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
    public String showSquareCreateFrom(@PathVariable String cid, Model model) {
        model.addAttribute("SquareForm", new SquareForm());
        // model.addAttribute("cid", cid);
        // テスト用リストの作成
        SquareEvent se1 = new SquareEvent((long) 1, 1, 1, "1マス進む");
        SquareEvent se2 = new SquareEvent((long) 2, 1, 2, "2マス進む");
        System.out.println(se1.getSquareEventID());
        System.out.println(se2.getSquareEventID());
        List<SquareEvent> SquareEventList = new ArrayList<>();
        SquareEventList.add(se1);
        SquareEventList.add(se2);
        model.addAttribute("SquareEventList", SquareEventList);
        model.addAttribute("cid", "test");
        return "creator_create";
    }

    @PostMapping("/{cid}/create/confirm")
    public String showSquareCreateConfirm(@PathVariable String cid, SquareForm form, Model model) {
        model.addAttribute("SquareForm", form);
        model.addAttribute("cid", cid);
        SquareEvent se1 = new SquareEvent((long) 1, 1, 1, "1マス進む");
        SquareEvent se2 = new SquareEvent((long) 2, 1, 2, "2マス進む");
        if (form.getSquareEventId() == 1) {
            model.addAttribute("se", se1);
        } else {
            model.addAttribute("se", se2);
        }

        return "creator_create_confirm";
    }

    @PostMapping("/{cid}/create/apply")
    public String showSquareCreateDonePag(SquareForm form) {
        System.out.println(form.getTitle());
        System.out.println(form.getDescription());
        return "creator_create_done";
    }

    @GetMapping("/{cid}/config")
    public String showSugorokuManu() {
        return "creator_sugoroku_config";
    }

}
