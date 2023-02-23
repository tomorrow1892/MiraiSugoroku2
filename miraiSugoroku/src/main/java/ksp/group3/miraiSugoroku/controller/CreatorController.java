package ksp.group3.miraiSugoroku.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ksp.group3.miraiSugoroku.entity.Event;
import ksp.group3.miraiSugoroku.entity.Square;
import ksp.group3.miraiSugoroku.entity.SquareCreator;
import ksp.group3.miraiSugoroku.entity.SquareEvent;
import ksp.group3.miraiSugoroku.form.CreatorLoginForm;
import ksp.group3.miraiSugoroku.form.GameConfigForm;
import ksp.group3.miraiSugoroku.form.UpdateSquareCreatorForm;
import ksp.group3.miraiSugoroku.repository.CreatorRepository;
import ksp.group3.miraiSugoroku.repository.EventRepository;
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
    @Autowired
    EventRepository eRepo;
    @Autowired
    CreatorRepository cRepo;

    @GetMapping("/loginpage")
    public String showcreatorLoginPage(@ModelAttribute("creatorLoginForm") CreatorLoginForm form, Model model) {
        List<Integer> years = createYearList();

        System.out.println(form);
        // 初期値は現在の年度にする
        if (form.getSelectedYear() == 0) {
            form.setSelectedYear(Calendar.getInstance().get(Calendar.YEAR));
        }

        model.addAttribute("creatorLoginForm", form);
        model.addAttribute("years", years);
        model.addAttribute("selectedYear", form.getSelectedYear());
        model.addAttribute("events", eService.getAllEvents());
        model.addAttribute("roll", "user");

        return "creator_login";
    }

    @GetMapping("/loginpage/{selectedYear}")
    public String redirectCreatorLoginPage(@PathVariable("selectedYear") int selectedYear,
            @ModelAttribute("creatorLoginForm") CreatorLoginForm form, Model model) {
        List<Integer> years = createYearList();

        model.addAttribute("creatorLoginForm", form);
        model.addAttribute("years", years);
        model.addAttribute("selectedYear", selectedYear);
        model.addAttribute("roll", "user");

        return "creator_login";
    }

    @PostMapping("/search")
    public String showEventList(@ModelAttribute("creatorLoginForm") CreatorLoginForm form,
            RedirectAttributes redirectAttributes, Model model) {
        List<Event> events = eService.filterEventsByYear(form.getSelectedYear());

        redirectAttributes.addFlashAttribute("events", events);

        return "redirect:/loginpage/" + form.getSelectedYear();
    }

    /*
     * イベントの開催年度一覧を作成する
     * その年~2022まで降順
     */
    private List<Integer> createYearList() {
        List<Integer> years = new ArrayList<>();

        Calendar calender = Calendar.getInstance();
        int latestYear = calender.get(Calendar.YEAR);
        int oldestYear = 2023;

        for (int i = latestYear; i >= oldestYear; i--) {
            years.add(i);
        }

        return years;
    }

    @PostMapping("/login")
    public String creatorLogin(@ModelAttribute("creatorLoginForm") CreatorLoginForm form, Model model) {
        SquareCreator sc = cService.login(form.getSelectedEventId(), form.getLoginId());

        if (Objects.isNull(sc.getNickname())) {
            return "redirect:/" + sc.getCreatorId() + "/detailFirst";
        }

        return "redirect:/" + sc.getCreatorId() + "/menu";
    }

    @GetMapping("/{creatorId}/menu")
    public String showcreatorMenu(@PathVariable("creatorId") Long creatorId, Model model) {

        // 作成権限があるかを確認
        Long eventId = cService.getSquareCreator(creatorId).getEventId();
        boolean bool = eService.getBoolean(eventId);

        model.addAttribute("bool", bool);
        model.addAttribute("event", eService.getEvent(eventId));
        model.addAttribute("cid", creatorId);
        String nickname = cService.getSquareCreator(creatorId).getNickname();
        model.addAttribute("nickname", nickname);
        model.addAttribute("roll", "creator");
        return "creator_menu";
    }

    @GetMapping("/{creatorId}/detail")
    public String showcreatorDetailForm(@ModelAttribute("updateSquareCreatorForm") UpdateSquareCreatorForm form,
            @PathVariable("creatorId") Long creatorId, Model model) {
        SquareCreator sc = cService.getSquareCreator(creatorId);
        Event e = eService.getEvent(sc.getEventId());
        List<Integer> groups = new ArrayList<>();

        for (int i = 1; i <= e.getNGroups(); i++) {
            groups.add(i);
        }

        model.addAttribute("groups", groups);
        model.addAttribute("cid", creatorId);
        model.addAttribute("roll", "creator");
        return "creator_profile";
    }

    @GetMapping("/{creatorId}/detailFirst")
    public String showcreatorDetailFormFirst(@ModelAttribute("updateSquareCreatorForm") UpdateSquareCreatorForm form,
            @PathVariable("creatorId") Long creatorId, Model model) {
        SquareCreator sc = cService.getSquareCreator(creatorId);
        Event e = eService.getEvent(sc.getEventId());
        List<Integer> groups = new ArrayList<>();

        for (int i = 1; i <= e.getNGroups(); i++) {
            groups.add(i);
        }

        model.addAttribute("groups", groups);
        model.addAttribute("roll", "user");
        return "creator_profile_first";
    }

    @PostMapping("/{creatorId}/update")
    public String updatecreatorDetail(@ModelAttribute("updateSquareCreatorForm") UpdateSquareCreatorForm form,
            @PathVariable("creatorId") Long creatorId, Model model) {
        cService.updateSquareCreator(creatorId, form);
        return "redirect:/" + creatorId + "/menu";
    }

    @GetMapping("/{cid}/squares")
    public String showSquare(@PathVariable("cid") String cid, Model model, @PageableDefault(60) Pageable pageable) {
        model.addAttribute("cid", cid);
        Page<Square> page = sService.getPageApprovedSquare(pageable, true);
        model.addAttribute("square_list", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("path", "/" + cid + "/squares");
        model.addAttribute("roll", "creator");
        List<Integer> years = createYearList();
        model.addAttribute("years", years);
        model.addAttribute("events", eService.getAllEvents());
        return "creator_squarelist";
    }

    @GetMapping("/{cid}/squares/mysquares")
    public String showMySquare(@PathVariable("cid") Long cid,
            @PageableDefault(60) Pageable pageable, Model model) {
        Page<Square> page = sService.searchPageSquaresByCreator(pageable, cid, true);
        model.addAttribute("square_list", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("path", "/" + cid + "/squares/mysquares");
        model.addAttribute("cid", cid);
        model.addAttribute("roll", "creator");
        List<Integer> years = createYearList();
        model.addAttribute("years", years);
        model.addAttribute("events", eService.getAllEvents());
        return "creator_squarelist";
    }

    @GetMapping("/{cid}/squares/search/keyword")
    public String searchSquaresByKeyword(@PathVariable("cid") String cid, @RequestParam("keyword") String keyword,
            @PageableDefault(60) Pageable pageable,
            Model model) {

        Page<Square> page = sService.searchPageSquaresByKeyword(pageable, keyword);
        model.addAttribute("square_list", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("path", "/" + cid + "/squares/search/keyword?keyword=" + keyword);
        model.addAttribute("cid", cid);
        model.addAttribute("roll", "creator");
        List<Integer> years = createYearList();
        model.addAttribute("years", years);
        model.addAttribute("events", eService.getAllEvents());
        return "creator_squarelist";
    }

    @GetMapping("/{cid}/squares/search/nickname")
    public String searchSquaresByNickname(@PathVariable("cid") String cid, @RequestParam("nickname") String nickname,
            @PageableDefault(60) Pageable pageable, Model model) {
        Page<Square> page = sService.searchPageSquaresByNickname(pageable, nickname);
        model.addAttribute("square_list", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("path", "/" + cid + "/squares/search/nickname?nickname=" + nickname);
        model.addAttribute("cid", cid);
        model.addAttribute("roll", "creator");
        List<Integer> years = createYearList();
        model.addAttribute("years", years);
        model.addAttribute("events", eService.getAllEvents());
        return "creator_squarelist";
    }

    @GetMapping("{cid}/squares/search/event")
    public String searchSquaresByEvent(@PathVariable("cid") String cid, @RequestParam("event") String event,
            @RequestParam("year") String year, @PageableDefault(60) Pageable pageable, Model model) {
        Long eventId = Long.parseLong(event);
        Page<Square> page = sService.searchPageSquaresByEventId(pageable, eventId);
        model.addAttribute("square_list", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("path", "/" + cid + "/squares/search/event?year=" + year + "&event=" + event);
        model.addAttribute("cid", cid);
        model.addAttribute("roll", "creator");
        List<Integer> years = createYearList();
        model.addAttribute("years", years);
        model.addAttribute("events", eService.getAllEvents());
        return "creator_squarelist";
    }

    @GetMapping("/{cid}/create") // マス作成画面を表示
    public String showSquareCreateFrom(@PathVariable Long cid, Model model) {
        model.addAttribute("SquareForm", new SquareForm());
        model.addAttribute("cid", cid);
        model.addAttribute("nickName", cService.getSquareCreator(cid).getNickname());
        List<SquareEvent> SquareEventList = seService.getSquareEventForCreate();
        model.addAttribute("SquareEventList", SquareEventList);
        model.addAttribute("roll", "creator");
        return "creator_create";
    }

    @PostMapping("/{cid}/create/confirm") // マス作成確認画面
    public String showSquareCreateConfirm(@PathVariable String cid, SquareForm form, Model model) {
        model.addAttribute("SquareForm", form);
        model.addAttribute("cid", cid);

        SquareEvent se = seService.getSquareEvent(form.getSquareEventId());
        model.addAttribute("se", se);
        model.addAttribute("roll", "creator");
        return "creator_create_confirm";
    }

    @PostMapping("/{cid}/create/apply") // マス作成申請完了画面
    public String showSquareCreateDonePag(@PathVariable Long cid, SquareForm form, Model model) {
        form.setCreatorId(cid);
        form.setEventId(cService.getSquareCreator(cid).getEventId());
        form.setGroupId(cService.getSquareCreator(cid).getGroup());
        form.setCreatorName(cService.getSquareCreator(cid).getNickname());
        model.addAttribute("cid", cid);
        sService.createSquare(form);
        model.addAttribute("roll", "creator");
        return "creator_create_done";
    }

    @GetMapping("/{cid}/config")
    public String showSugorokuManu(@PathVariable Long cid, Model model) {
        model.addAttribute("GameConfigForm", new GameConfigForm());
        model.addAttribute("cid", cid);
        model.addAttribute("roll", "creator");
        List<Event> eventIdList = eService.getAllEvents();
        model.addAttribute("eventIdList", eventIdList);
        return "creator_sugoroku_config";
    }

}