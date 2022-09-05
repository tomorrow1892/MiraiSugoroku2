package ksp.group3.miraiSugoroku.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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

        // Admin実装前の各種データ登録用
        if (eRepo.count() == 0) {
            System.out.println("Created Events!");
            Calendar cl = Calendar.getInstance();

            for (int i = 2022; i >= 2020; i--) {
                cl.set(Calendar.YEAR, i);
                Event e = new Event(null, 5, cl.getTime(), cl.getTime(), "Test event-" + String.valueOf(i), true);

                eRepo.save(e);
                System.out.println("save OK");
            }
        }

        if (cRepo.count() == 0) {
            System.out.println("Created SquareCreator!");
            SquareCreator sc1 = new SquareCreator(null, "taro", 1L, 0, true, "Taro", null);
            SquareCreator sc2 = new SquareCreator(null, "hanako", 2L, 0, true, "Hanako", null);
            SquareCreator sc3 = new SquareCreator(null, "mika", 3L, 0, true, "Mika", null);

            cRepo.save(sc1);
            cRepo.save(sc2);
            cRepo.save(sc3);
        }

        return "creator_login";
    }

    @GetMapping("/loginpage/{selectedYear}")
    public String redirectCreatorLoginPage(@PathVariable("selectedYear") int selectedYear,
            @ModelAttribute("creatorLoginForm") CreatorLoginForm form, Model model) {
        List<Integer> years = createYearList();

        model.addAttribute("creatorLoginForm", form);
        model.addAttribute("years", years);
        model.addAttribute("selectedYear", selectedYear);

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
        int oldestYear = 2020;

        for (int i = latestYear; i >= oldestYear; i--) {
            years.add(i);
        }

        return years;
    }

    @PostMapping("/login")
    public String creatorLogin(@ModelAttribute("creatorLoginForm") CreatorLoginForm form, Model model) {
        SquareCreator sc = cService.getSquareCreatorByEventIdAndLoginId(form.getSelectedEventId(), form.getLoginId());

        if (Objects.isNull(sc.getNickname())) {
            return "redirect:/" + sc.getCreatorId() + "/detail";
        }

        return "redirect:/" + sc.getCreatorId() + "/menu";
    }

    @GetMapping("/{creatorId}/menu")
    public String showcreatorMenu(@PathVariable("creatorId") String creatorId, Model model) {
        model.addAttribute("cid", creatorId);
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

        return "creator_profile";
    }

    @PostMapping("/{creatorId}/update")
    public String updatecreatorDetail(@ModelAttribute("updateSquareCreatorForm") UpdateSquareCreatorForm form,
            @PathVariable("creatorId") Long creatorId, Model model) {
        cService.updateSquareCreator(creatorId, form);
        return "redirect:/" + creatorId + "/menu";
    }

    @GetMapping("/{cid}/squares")
    public String showSquare(@PathVariable("cid") String cid, Model model) {
        List<Square> square_list = sService.filterSquaresByIsApproved(true);
        model.addAttribute("square_list", square_list);
        model.addAttribute("cid", cid);
        return "creator_squarelist";
    }

    // 検索結果の内承認済みのやつだけ表示できるように今後修正
    @GetMapping("/{cid}/squares/search/keyword")
    public String searchSquaresByKeyword(@RequestParam("keyword") String keyword, Model model) {
        List<Square> square_list = sService.searchSquaresByKeyword(keyword);
        model.addAttribute("square_list", square_list);

        return "creator_squarelist";
    }

    // 検索結果の内承認済みのやつだけ表示できるように今後修正
    @GetMapping("/{cid}/squares/search/nickname")
    public String searchSquaresByNickname(@RequestParam("nickname") String nickname, Model model) {
        List<Square> square_list = sService.searchSquaresByNickname(nickname);
        model.addAttribute("square_list", square_list);

        return "creator_squarelist";
    }

    @GetMapping("/{cid}/create") // マス作成画面を表示
    public String showSquareCreateFrom(@PathVariable String cid, Model model) {
        model.addAttribute("SquareForm", new SquareForm());
        model.addAttribute("cid", cid);
        List<SquareEvent> SquareEventList = seService.getAllSquareEvent();
        SquareEventList.remove(13);
        model.addAttribute("SquareEventList", SquareEventList);
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
    public String showSquareCreateDonePag(@PathVariable Long cid, SquareForm form) {
        form.setCreatorId(cid);
        sService.createSquare(form);
        return "creator_create_done";
    }

    @GetMapping("/{cid}/config")
    public String showSugorokuManu() {
        return "creator_sugoroku_config";
    }

}
