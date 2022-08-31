package ksp.group3.miraiSugoroku.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ksp.group3.miraiSugoroku.entity.Event;
import ksp.group3.miraiSugoroku.entity.Square;
import ksp.group3.miraiSugoroku.entity.SquareCreator;
import ksp.group3.miraiSugoroku.form.AdminLoginForm;
import ksp.group3.miraiSugoroku.form.EventForm;
import ksp.group3.miraiSugoroku.form.SquareCreatorForm;
import ksp.group3.miraiSugoroku.service.CreatorService;
import ksp.group3.miraiSugoroku.service.EventService;
import ksp.group3.miraiSugoroku.service.SquareService;

@Controller
public class AdminController {
    @Autowired
    EventService eService;
    @Autowired
    CreatorService cService;
    @Autowired
    SquareService sService;

    // 管理者ログインページ
    // パスワード未実装
    @GetMapping("/admin")
    public String showAdminLodinPage(Model model) {
        AdminLoginForm adform = new AdminLoginForm();
        model.addAttribute("AdminLoginForm", adform);
        return "admin_login";
    }

    // ログイン
    @PostMapping("/admin/login")
    public String adminLogin(AdminLoginForm loginform, Model model) {
        // パスワード未設定
        return "redirect:/admin/menu";
    }

    // メニューページ
    @GetMapping("/admin/menu")
    public String showMenuPage(Model model) {
        // 期限内のイベントを取得
        Date date = new Date();
        List<Event> eventList = eService.getActiveEvents(date);
        model.addAttribute("eventList", eventList);
        return "admin_menu";
    }

    // イベント作成画面
    @GetMapping("/admin/createEvent")
    public String showCreateEventForm(Model model) {
        EventForm eventform = new EventForm();
        model.addAttribute("EventForm", eventform);
        return "admin_create_event";
    }

    // イベント作成確認画面
    @PostMapping("/admin/confirmEvent")
    public String showConfirmEventForm(EventForm eventform, Model model) {
        // Event event = eventform.toEntity();
        System.out.println(eventform.getNGroups());
        System.out.println(eventform.getName());
        System.out.println(eventform.getLimitDate());
        model.addAttribute("Eventform", eventform);
        return "admin_confirm_event";
    }

    // イベント作成完了画面
    @PostMapping("/admin/registerEvent")
    public String showRegistertEvent(EventForm eventform, Model model) {
        Event event = eService.createEvent(eventform);
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

        List<Square> slist = sService.filterSquaresByEventIdAndIsApproved(eventId, false);
        String str = "未承認マス" + slist.size() + "個";
        model.addAttribute("str", str);

        return "admin_event";
    }

    // 参加者（作成者）の追加
    @PostMapping("/admin/event/{eventId}/creator/register")
    public String registerSquareCreator(@PathVariable Long eventId, SquareCreatorForm scform, Model model) {
        // 一応イベント内のログインIDが重複しないように
        // ただし重複した場合のエラー文の表示なし
        SquareCreator sc = cService.getSquareCreatorByEventIdAndLoginId(eventId, scform.getLoginId());
        System.out.println(sc);
        if (sc == null) {
            cService.createSquareCreator(scform);
        }
        return "redirect:/admin/event/" + eventId;
    }

    // 参加者（作成者）の削除
    @GetMapping("/admin/event/{eventId}/creator/delete/{creatorId}")
    public String deleteSquareCreator(@PathVariable Long eventId, @PathVariable Long creatorId, Model model) {
        cService.deleteSquareCreator(creatorId);
        return "redirect:/admin/event/" + eventId;
    }

    @GetMapping("/admin/event/{eventId}/approve")
    public String approveSquare(@PathVariable Long eventId, Model model) {
        List<Square> slist = sService.filterSquaresByEventIdAndIsApproved(eventId, false);
        List<SquareCreator> clist = new ArrayList<SquareCreator>();
        for (int i = 0; i < slist.size(); i++) {
            clist.add(cService.getSquareCreator(slist.get(i).getCreatorId()));
        }
        model.addAttribute("slist", slist);
        model.addAttribute("clist", clist);
        return "admin_approve_Square";
    }

}
