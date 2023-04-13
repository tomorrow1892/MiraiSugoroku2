package ksp.group3.miraiSugoroku.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ksp.group3.miraiSugoroku.entity.Event;
import ksp.group3.miraiSugoroku.entity.Square;
import ksp.group3.miraiSugoroku.entity.SquareCreator;
import ksp.group3.miraiSugoroku.entity.SquareEvent;
import ksp.group3.miraiSugoroku.form.CreatorAndSquareDTO;
import ksp.group3.miraiSugoroku.form.SquareForm;
import ksp.group3.miraiSugoroku.security.UserService;
import ksp.group3.miraiSugoroku.security.UserSession;
import ksp.group3.miraiSugoroku.service.CreatorService;
import ksp.group3.miraiSugoroku.service.EventService;
import ksp.group3.miraiSugoroku.service.SquareEventService;
import ksp.group3.miraiSugoroku.service.SquareService;

@Controller
public class SubAdminController {
    @Autowired
    EventService eService;
    @Autowired
    CreatorService cService;
    @Autowired
    SquareService sService;
    @Autowired
    SquareEventService seService;
    @Autowired
    UserService uService;

    @GetMapping("/subadmin")
    public String subadminLogin() {
        return "subadmin_login";
    }

    @GetMapping("/subadmin/menu")
    public String showSubadminMenu(@AuthenticationPrincipal UserSession session, Model model) {
        Long uid = uService.getUser(session.getUsername()).getUid();
        Event event = eService.getEventByUid(uid);
        Long eventId = event.getEventId();
        List<SquareCreator> creator_list = cService.getSquareCreatorsByEventId(eventId);
        model.addAttribute("event", event);
        model.addAttribute("eventid", eventId);
        model.addAttribute("clist", creator_list);
        boolean bool = eService.getBoolean(eventId);
        model.addAttribute("bool", bool);
        List<Square> slist = sService.filterSquaresByEventIdAndIsApproved(eventId, false);
        String str1 = "未承認マス" + slist.size() + "個";
        model.addAttribute("str1", str1);
        String str2 = "グループ数：" + event.getNGroups();
        model.addAttribute("str2", str2);
        return "subadmin_menu";
    }

    // 承認済マス一覧
    @GetMapping("/subadmin/event/{eventId}/squarelist")
    public String adminSquareList(@PathVariable Long eventId, Model model) {
        List<CreatorAndSquareDTO> dtolist = new ArrayList<CreatorAndSquareDTO>();
        List<Square> slist = sService.filterSquaresByEventIdAndIsApproved(eventId, true);

        for (int i = 0; i < slist.size(); i++) {
            CreatorAndSquareDTO dto = new CreatorAndSquareDTO();
            SquareCreator sc = cService.getSquareCreator(slist.get(i).getCreatorId());
            String name = "";
            String nickname = "";
            if (sc == null) {
                name = "削除済のユーザ";
                nickname = "削除済のユーザ";
            } else {
                name = sc.getLoginId();
                nickname = sc.getNickname();
            }
            dto.setName(name);
            dto.setNickname(nickname);
            dto.setTitle(slist.get(i).getTitle());
            dto.setSquareId(slist.get(i).getSquareId());
            dtolist.add(dto);
        }
        model.addAttribute("eventId", eventId);
        model.addAttribute("dtolist", dtolist);
        model.addAttribute("roll", "admin");
        return "subadmin_squarelist";
    }

    // 未承認マス一覧
    @GetMapping("/subadmin/event/{eventId}/approve")
    public String approveSquareList(@PathVariable Long eventId, Model model) {
        List<CreatorAndSquareDTO> dtolist = new ArrayList<CreatorAndSquareDTO>();
        List<Square> slist = sService.filterSquaresByEventIdAndIsApproved(eventId, false);
        for (int i = 0; i < slist.size(); i++) {
            CreatorAndSquareDTO dto = new CreatorAndSquareDTO();
            SquareCreator sc = cService.getSquareCreator(slist.get(i).getCreatorId());
            String name = "";
            String nickname = "";
            if (sc == null) {
                name = "削除済のユーザ";
                nickname = "削除済のユーザ";
            } else {
                name = sc.getLoginId();
                nickname = sc.getNickname();
            }
            dto.setName(name);
            dto.setNickname(nickname);
            dto.setTitle(slist.get(i).getTitle());
            dto.setSquareId(slist.get(i).getSquareId());
            dtolist.add(dto);
        }
        model.addAttribute("eventId", eventId);
        model.addAttribute("dtolist", dtolist);
        model.addAttribute("roll", "admin");
        return "subadmin_approve_square";
    }

    // 承認マス削除確認
    @GetMapping("/subadmin/event/{eventId}/squarelist/{squareId}")
    public String deleteConfirmSquare(@PathVariable Long eventId, @PathVariable Long squareId, Model model) {
        SquareForm sf = new SquareForm();
        Square s = sService.getSquare(squareId);
        sf.setTitle(s.getTitle());
        sf.setDescription(s.getDescription());
        sf.setCreatorName(s.getNickName());
        sf.setSquareEventId(s.getSquareEventId());
        sf.setPicture(s.getPicture());
        sf.setSquareEffect(sf.determineKindOfSquare(s.getSquareEventId()));
        model.addAttribute("square", s);
        model.addAttribute("SquareForm", sf);
        model.addAttribute("squareId", squareId);
        model.addAttribute("eventId", eventId);
        model.addAttribute("roll", "admin");
        return "subadmin_square_delete";
    }

    // 承認マス削除
    @GetMapping("/subadmin/event/{eventId}/squarelist/{squareId}/delete")
    public String deleteSquare(@PathVariable Long eventId, @PathVariable Long squareId, Model model) {
        sService.deleteSquare(squareId);
        model.addAttribute("eventId", eventId);
        model.addAttribute("roll", "admin");
        return "subadmin_square_delete_done";
    }

    // 未承認マス詳細
    @GetMapping("/subadmin/event/{eventId}/approve/{squareId}")
    public String approveSquare(@PathVariable Long eventId, @PathVariable Long squareId, Model model) {
        Square s = sService.getSquare(squareId);
        SquareForm sf = new SquareForm();
        sf.setTitle(s.getTitle());
        sf.setDescription(s.getDescription());
        sf.setSquareEventId(s.getSquareEventId());
        sf.setPicture(s.getPicture());
        sf.setSquareEffect(sf.determineKindOfSquare(s.getSquareEventId()));
        model.addAttribute("sf", sf);
        List<SquareEvent> SquareEventList = seService.getSquareEventForCreate();
        model.addAttribute("SquareEventList", SquareEventList);

        String name = cService.getSquareCreator(s.getCreatorId()).getNickname();
        model.addAttribute("name", name);
        model.addAttribute("squareId", squareId);
        model.addAttribute("eventId", eventId);
        model.addAttribute("roll", "admin");
        return "subadmin_edit_square";
    }

    // マス承認確認
    @PostMapping("/subadmin/event/{eventId}/approve/{squareId}/confirm")
    public String confirmSquare(@PathVariable Long eventId, @PathVariable Long squareId, SquareForm sf, Model model) {
        sf.setSquareEffect(sf.determineKindOfSquare(sf.getSquareEventId()));
        model.addAttribute("sf", sf);
        List<SquareEvent> SquareEventList = seService.getSquareEventForCreate();
        model.addAttribute("SquareEventList", SquareEventList);
        Square s = sService.getSquare(squareId);
        String name = cService.getSquareCreator(s.getCreatorId()).getName();
        model.addAttribute("name", name);
        model.addAttribute("squareId", squareId);
        model.addAttribute("eventId", eventId);
        model.addAttribute("roll", "admin");
        return "subadmin_confirm_square";
    }

    // マス承認拒否
    @GetMapping("/subadmin/event/{eventId}/approve/{squareId}/decline")
    public String declineSquare(@PathVariable Long eventId, @PathVariable Long squareId, Model model) {
        SquareForm sf = new SquareForm();
        Square s = sService.getSquare(squareId);
        sf.setTitle(s.getTitle());
        sf.setDescription(s.getDescription());
        sf.setCreatorName(s.getNickName());
        sf.setSquareEventId(s.getSquareEventId());
        sf.setPicture(s.getPicture());
        model.addAttribute("square", s);
        model.addAttribute("SquareForm", sf);
        model.addAttribute("squareId", squareId);
        model.addAttribute("eventId", eventId);
        model.addAttribute("roll", "admin");
        return "subadmin_approve_square_decline";
    }

    // マス承認拒否完了
    @GetMapping("/subadmin/event/{eventId}/approve/{squareId}/decline/done")
    public String declineDoneSquare(@PathVariable Long eventId, @PathVariable Long squareId, Model model) {
        sService.deleteSquare(squareId);
        model.addAttribute("eventId", eventId);
        model.addAttribute("roll", "admin");
        return "subadmin_approve_square_decline_done";
    }

    // マス承認完了
    @PostMapping("/subadmin/event/{eventId}/approve/{squareId}/done")
    public String doneSquare(@PathVariable Long eventId, @PathVariable Long squareId, SquareForm sf, Model model) {
        sf.setApproved(true);
        sService.updateSquare(squareId, sf);
        return "subadmin_done_square";
    }
}