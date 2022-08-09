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
import ksp.group3.miraiSugoroku.entity.SquareCreator;
import ksp.group3.miraiSugoroku.entity.SquareEvent;
import ksp.group3.miraiSugoroku.form.SquareCreatorForm;
import ksp.group3.miraiSugoroku.form.SquareForm;
import ksp.group3.miraiSugoroku.service.CreatorService;
import ksp.group3.miraiSugoroku.service.EventService;
import ksp.group3.miraiSugoroku.service.SquareEventService;
import ksp.group3.miraiSugoroku.service.SquareService;

@Controller
public class CreatorController {
    @Autowired
    EventService eService;
    @Autowired
    CreatorService cService;
    @Autowired
    SquareService sService;
    @Autowired
    SquareEventService seService;

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

    @GetMapping("/{cid}/create") // マス作成画面を表示
    public String showSquareCreateFrom(@PathVariable String cid, Model model) {
        model.addAttribute("SquareForm", new SquareForm());
        // model.addAttribute("cid", cid);
        List<SquareEvent> SquareEventList = seService.getAllSquareEvent();
        SquareEventList.remove(13);
        model.addAttribute("SquareEventList", SquareEventList);
        model.addAttribute("cid", "test");
        return "creator_create";
    }

    @PostMapping("/{cid}/create/confirm") // マス作成確認画面
    public String showSquareCreateConfirm(@PathVariable String cid, SquareForm form, Model model) {
        model.addAttribute("SquareForm", form);
        model.addAttribute("cid", cid);

        SquareEvent se = seService.getSquareEvent(form.getSquareEventId());
        model.addAttribute("se", se);

        return "creator_create_confirm";
    }

    @PostMapping("/{cid}/create/apply") // マス作成申請完了画面
    public String showSquareCreateDonePag(SquareForm form) {
        // System.out.println(form.getTitle());
        // System.out.println(form.getDescription());
        // System.out.println(form.getSquareEventId());
        form.setApproved(true);
        sService.createSquare(form);
        return "creator_create_done";
    }

    @GetMapping("/{cid}/config")
    public String showSugorokuManu() {
        return "creator_sugoroku_config";
    }

}
