package ksp.group3.miraiSugoroku.controller;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ksp.group3.miraiSugoroku.entity.Event;
import ksp.group3.miraiSugoroku.entity.SquareCreator;
import ksp.group3.miraiSugoroku.form.CreatorLoginForm;
import ksp.group3.miraiSugoroku.repository.CreatorRepository;
import ksp.group3.miraiSugoroku.repository.EventRepository;
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
    @Autowired
    EventRepository eRepo;
    @Autowired
    CreatorRepository cRepo;

    @GetMapping("/loginpage")
    public String showcreatorLoginPage( @ModelAttribute("creatorLoginForm") CreatorLoginForm form,Model model) {
        List<Integer> years = createYearList();

        System.out.println(form);
        //初期値は現在の年度にする
        if (form.getSelectedYear() == 0) {
            form.setSelectedYear(Calendar.getInstance().get(Calendar.YEAR));
        }

        model.addAttribute("creatorLoginForm", form);
        model.addAttribute("years", years);
        model.addAttribute("selectedYear", form.getSelectedYear());

        //Admin実装前の各種データ登録用
        if (eRepo.count() == 0) {
            Calendar cl = Calendar.getInstance();

            for (int i = 2022; i >= 2020; i--)
            {
                cl.set(Calendar.YEAR, i);
                Event e = new Event(null, 0, cl.getTime(), cl.getTime(), "Test event-" + String.valueOf(i), true);
                
                eRepo.save(e);
            }
        }

        if (cRepo.count() == 0) {
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
    public String redirectCreatorLoginPage(@PathVariable("selectedYear") int selectedYear, @ModelAttribute("creatorLoginForm") CreatorLoginForm form,Model model) {
        List<Integer> years = createYearList();

        model.addAttribute("creatorLoginForm", form);
        model.addAttribute("years", years);
        model.addAttribute("selectedYear", selectedYear);

        return "creator_login";
    }

    @PostMapping("/search")
    public String showEventList(@ModelAttribute("createrLoginForm") CreatorLoginForm form, RedirectAttributes redirectAttributes, Model model) {
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

        for(int i = latestYear; i >= oldestYear; i--) {
            years.add(i);
        }

        return years;
    }

    @PostMapping("/login")
    public String creatorLogin(@ModelAttribute("creatorLoginForm") CreatorLoginForm form, Model model) {
        System.out.println(form.getSelectedEventId());
        System.out.println(form.getLoginId());
        SquareCreator sc = cService.getSquareCreatorByEventIdAndLoginId(form.getSelectedEventId(), form.getLoginId());

        return "redirect:/" + sc.getCreatorId() + "/manu";
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
