package ksp.group3.miraiSugoroku.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ksp.group3.miraiSugoroku.exception.MiraiSugorokuException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ksp.group3.miraiSugoroku.entity.Event;
import ksp.group3.miraiSugoroku.entity.Square;
import ksp.group3.miraiSugoroku.entity.SquareCreator;
import ksp.group3.miraiSugoroku.entity.SquareEvent;
import ksp.group3.miraiSugoroku.form.AdminLoginForm;
import ksp.group3.miraiSugoroku.form.CreatorAndSquareDTO;
import ksp.group3.miraiSugoroku.form.EventForm;
import ksp.group3.miraiSugoroku.form.SquareCreatorForm;
import ksp.group3.miraiSugoroku.form.SquareForm;
import ksp.group3.miraiSugoroku.security.User;
import ksp.group3.miraiSugoroku.security.UserService;
import ksp.group3.miraiSugoroku.security.UserSession;
import ksp.group3.miraiSugoroku.service.CreatorService;
import ksp.group3.miraiSugoroku.service.EventService;
import ksp.group3.miraiSugoroku.service.SquareEventService;
import ksp.group3.miraiSugoroku.service.SquareService;

@Controller
public class AdminController {
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

    // 管理者ログインページ
    // パスワード未実装
    @GetMapping("/admin")
    public String showAdminLodinPage(Model model) {
        return "admin_login";
    }

    // ログイン
    @PostMapping("/admin/login")
    public String adminLogin(AdminLoginForm loginform, Model model) {

        if (!(loginform.getPassword().equals("miraisanda"))) {
            throw new MiraiSugorokuException(MiraiSugorokuException.ADMIN_PASSWORD_WRONG, "");
        }
        return "redirect:/admin/menu";
    }

    // メニューページ
    @GetMapping("/admin/menu")
    public String showMenuPage(Model model) {
        // 期限内のイベントを取得
        Date date = new Date();
        List<Event> eventList = eService.getActiveEvents(date);
        model.addAttribute("eventList", eventList);

        // 終了済イベント
        List<Event> endList = eService.getNotActiveEvents(date);
        model.addAttribute("endList", endList);
        model.addAttribute("roll", "admin");
        return "admin_menu";
    }

    // イベント作成画面
    @GetMapping("/admin/createEvent")
    public String showCreateEventForm(Model model) {
        EventForm eventform = new EventForm();
        model.addAttribute("EventForm", eventform);
        model.addAttribute("roll", "admin");

        return "admin_create_event";
    }

    // イベント作成確認画面
    @PostMapping("/admin/confirmEvent")
    public String showConfirmEventForm(EventForm eventform, Model model) {
        model.addAttribute("Eventform", eventform);
        model.addAttribute("roll", "admin");
        return "admin_confirm_event";
    }

    // イベント作成完了画面
    @PostMapping("/admin/registerEvent")
    public String showRegistertEvent(EventForm eventform, Model model) {
        eService.createEvent(eventform);
        model.addAttribute("roll", "admin");
        return "admin_register_event";
    }

    // イベントの詳細に入る
    @GetMapping("/admin/event/{eventId}")
    public String showEventDetail(@PathVariable Long eventId, Model model) {
        Event event = eService.getEvent(eventId);
        List<SquareCreator> creator_list = cService.getSquareCreatorsByEventId(eventId);
        SquareCreatorForm scform = new SquareCreatorForm();
        model.addAttribute("event", event);
        model.addAttribute("eventid", eventId);
        model.addAttribute("clist", creator_list);
        model.addAttribute("scform", scform);
        boolean bool = eService.getBoolean(eventId);
        model.addAttribute("bool", bool);
        List<Square> slist = sService.filterSquaresByEventIdAndIsApproved(eventId, false);
        String str1 = "未承認マス" + slist.size() + "個";
        model.addAttribute("str1", str1);
        String str2 = "グループ数：" + event.getNGroups();
        model.addAttribute("str2", str2);

        model.addAttribute("roll", "admin");
        return "admin_event";
    }

    // イベント情報編集
    @GetMapping("/admin/event/{eventId}/edit")
    public String editEvent(@PathVariable Long eventId, Model model) {
        Event event = eService.getEvent(eventId);
        model.addAttribute("event", event);

        EventForm ef = new EventForm();
        ef.setName(event.getName());
        ef.setLimitDate(event.getLimitDate().toString());
        ef.setNGroups(event.getNGroups());
        model.addAttribute("ef", ef);
        model.addAttribute("roll", "admin");
        return "admin_event_edit";
    }

    // イベント情報更新
    @PostMapping("/admin/event/{eventId}/edit/confirm")
    public String editDoneEvent(@PathVariable Long eventId, EventForm eventform, Model model) {

        eService.updateEvent(eventId, eventform);
        return "redirect:/admin/event/" + eventId;
    }

    // 承認済マス一覧
    @GetMapping("/admin/event/{eventId}/squarelist")
    public String adminSquareList(@PathVariable Long eventId, Model model) {
        List<CreatorAndSquareDTO> dtolist = new ArrayList<CreatorAndSquareDTO>();
        List<Square> slist = sService.filterSquaresByEventIdAndIsApproved(eventId, true);

        for (int i = 0; i < slist.size(); i++) {
            CreatorAndSquareDTO dto = new CreatorAndSquareDTO();
            SquareCreator sc = cService.getSquareCreator(slist.get(i).getCreatorId());
            String name = "";
            if (sc == null) {
                name = "削除済のユーザ";
            } else {
                name = sc.getLoginId();
            }
            dto.setName(name);
            dto.setTitle(slist.get(i).getTitle());
            dto.setSquareId(slist.get(i).getSquareId());
            dtolist.add(dto);
        }
        model.addAttribute("eventId", eventId);
        model.addAttribute("dtolist", dtolist);
        model.addAttribute("roll", "admin");
        return "admin_squarelist";
    }

    // 未承認マス一覧
    @GetMapping("/admin/event/{eventId}/approve")
    public String approveSquareList(@PathVariable Long eventId, Model model) {
        List<CreatorAndSquareDTO> dtolist = new ArrayList<CreatorAndSquareDTO>();
        List<Square> slist = sService.filterSquaresByEventIdAndIsApproved(eventId, false);
        for (int i = 0; i < slist.size(); i++) {
            CreatorAndSquareDTO dto = new CreatorAndSquareDTO();
            SquareCreator sc = cService.getSquareCreator(slist.get(i).getCreatorId());
            String name = "";
            if (sc == null) {
                name = "削除済のユーザ";
            } else {
                name = sc.getLoginId();
            }
            dto.setName(name);
            dto.setTitle(slist.get(i).getTitle());
            dto.setSquareId(slist.get(i).getSquareId());
            dtolist.add(dto);
        }
        model.addAttribute("eventId", eventId);
        model.addAttribute("dtolist", dtolist);
        model.addAttribute("roll", "admin");
        return "admin_approve_square";
    }

    // 承認マス削除確認
    @GetMapping("/admin/event/{eventId}/squarelist/{squareId}")
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
        return "admin_square_delete";
    }

    // 承認マス削除
    @GetMapping("/admin/event/{eventId}/squarelist/{squareId}/delete")
    public String deleteSquare(@PathVariable Long eventId, @PathVariable Long squareId, Model model) {
        sService.deleteSquare(squareId);
        model.addAttribute("eventId", eventId);
        model.addAttribute("roll", "admin");
        return "admin_square_delete_done";
    }

    // 未承認マス詳細
    @GetMapping("/admin/event/{eventId}/approve/{squareId}")
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
        return "admin_edit_square";
    }

    // マス承認確認
    @PostMapping("/admin/event/{eventId}/approve/{squareId}/confirm")
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
        return "admin_confirm_square";
    }

    // マス承認拒否
    @GetMapping("/admin/event/{eventId}/approve/{squareId}/decline")
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
        return "admin_approve_square_decline";
    }

    // マス承認拒否
    @GetMapping("/admin/event/{eventId}/approve/{squareId}/decline/done")
    public String declineDoneSquare(@PathVariable Long eventId, @PathVariable Long squareId, Model model) {
        sService.deleteSquare(squareId);
        model.addAttribute("eventId", eventId);
        model.addAttribute("roll", "admin");
        return "admin_approve_square_decline_done";
    }

    @PostMapping("/admin/event/{eventId}/approve/{squareId}/done")
    public String doneSquare(@PathVariable Long eventId, @PathVariable Long squareId, SquareForm sf, Model model) {
        sf.setApproved(true);
        sService.updateSquare(squareId, sf);
        model.addAttribute("roll", "admin");
        return "admin_done_square";
    }

    // 参加者（作成者）の追加
    @PostMapping("/admin/event/{eventId}/creator/register")
    public String registerSquareCreator(@PathVariable Long eventId, SquareCreatorForm scform, Model model) {
        // 一応イベント内のログインIDが重複しないように
        // ただし重複した場合のエラー文の表示なし
        SquareCreator sc = cService.getSquareCreatorByEventIdAndLoginId(eventId, scform.getLoginId());
        if (sc == null) {
            cService.createSquareCreator(scform);
        }
        return "redirect:/admin/event/" + eventId;
    }

    // 参加者（作成者）の削除の確認
    @GetMapping("/admin/event/{eventId}/creator/delete/confirm/{creatorId}")
    public String deleteConfirmSquareCreator(@PathVariable Long eventId, @PathVariable Long creatorId, Model model) {
        model.addAttribute("eventid", eventId);
        SquareCreator sc = cService.getSquareCreator(creatorId);
        model.addAttribute("sc", sc);
        // cService.deleteSquareCreator(creatorId);
        model.addAttribute("roll", "admin");
        return "admin_creator_delete";
    }

    // 参加者（作成者）の削除
    @GetMapping("/admin/event/{eventId}/creator/delete/{creatorId}")
    public String deleteSquareCreator(@PathVariable Long eventId, @PathVariable Long creatorId, Model model) {
        cService.deleteSquareCreator(creatorId);
        return "redirect:/admin/event/" + eventId;
    }

    @GetMapping("/loginerror")
    public String loginError() {
        return "error";
    }

   
}
