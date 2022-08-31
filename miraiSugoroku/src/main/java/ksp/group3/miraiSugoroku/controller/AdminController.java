package ksp.group3.miraiSugoroku.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ksp.group3.miraiSugoroku.entity.Event;
import ksp.group3.miraiSugoroku.form.AdminLoginForm;
import ksp.group3.miraiSugoroku.form.EventForm;
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
        System.out.println(eventform.getNGroups());
        System.out.println(eventform.getName());
        System.out.println(eventform.getLimitDate());
        Event event = eService.createEvent(eventform);
        return "admin_register_event";
    }

}
