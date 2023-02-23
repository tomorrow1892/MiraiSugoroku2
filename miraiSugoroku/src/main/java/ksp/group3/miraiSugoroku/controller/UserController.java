package ksp.group3.miraiSugoroku.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import ksp.group3.miraiSugoroku.service.CreatorService;
import ksp.group3.miraiSugoroku.service.EventService;
import ksp.group3.miraiSugoroku.service.SquareEventService;
import ksp.group3.miraiSugoroku.service.SquareService;
import ksp.group3.miraiSugoroku.entity.Event;
import ksp.group3.miraiSugoroku.entity.Square;
import ksp.group3.miraiSugoroku.entity.SquareCreator;
import ksp.group3.miraiSugoroku.form.GameConfigForm;

@Controller
public class UserController {
    @Autowired
    SquareService sService;
    @Autowired
    SquareEventService seService;
    @Autowired
    CreatorService cService;
    @Autowired
    EventService eService;

    @GetMapping("/")
    public String showIndexPage(Model model) {
        seService.registerSquareEvent();
        model.addAttribute("roll", "user");
        return "index";
    }

    @GetMapping("/squares")
    public String showGuestSquareListPage(Model model, @PageableDefault(60) Pageable pageable) {
        // List<Square> square_list = sService.filterSquaresByIsApproved(true);
        // model.addAttribute("square_list", square_list);

        Page<Square> page = sService.getPageApprovedSquare(pageable, true);
        model.addAttribute("square_list", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("path", "/squares");
        model.addAttribute("roll", "user");
        List<Integer> years = createYearList();
        model.addAttribute("years", years);
        model.addAttribute("events", eService.getAllEvents());
        model.addAttribute("search", 0);
        return "guest_squarelist";
    }

    @GetMapping("square/{squareId}")
    public String showSquareDetail(@PathVariable Long squareId, Model model) {
        Square square = sService.getSquare(squareId);
        model.addAttribute("square", square);
        SquareCreator creator = cService.getSquareCreator(square.getCreatorId());
        model.addAttribute("creator", creator);
        model.addAttribute("roll", "user");
        List<Integer> years = createYearList();
        model.addAttribute("years", years);
        model.addAttribute("events", eService.getAllEvents());
        return "square_detail";
    }

    // 検索結果の内承認済みのやつだけ表示できるように今後修正
    @GetMapping("/squares/search/keyword")
    public String searchSquaresByKeyword(@RequestParam("keyword") String keyword, Model model, Pageable pageable) {
        // List<Square> square_list = sService.searchSquaresByKeyword(keyword);
        // model.addAttribute("square_list", square_list);

        Page<Square> page = sService.searchPageSquaresByKeyword(pageable, keyword);
        model.addAttribute("square_list", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("path", "/squares/search/keyword?keyword=" + keyword);
        model.addAttribute("roll", "user");
        List<Integer> years = createYearList();
        model.addAttribute("years", years);
        model.addAttribute("events", eService.getAllEvents());
        model.addAttribute("search", 1);
        model.addAttribute("kw", keyword);
        return "guest_squarelist";
    }

    // 検索結果の内承認済みのやつだけ表示できるように今後修正
    @GetMapping("/squares/search/nickname")
    public String searchSquaresByNickname(@RequestParam("nickname") String nickname, Model model, Pageable pageable) {
        // List<Square> square_list = sService.searchSquaresByNickname(nickname);
        // model.addAttribute("square_list", square_list);

        Page<Square> page = sService.searchPageSquaresByNickname(pageable, nickname);
        model.addAttribute("square_list", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("path", "/squares/search/nickname?nickname=" + nickname);
        model.addAttribute("roll", "user");
        List<Integer> years = createYearList();
        model.addAttribute("years", years);
        model.addAttribute("events", eService.getAllEvents());
        model.addAttribute("search", 2);
        model.addAttribute("nn", nickname);
        return "guest_squarelist";
    }

    // 検索結果の内承認済みのやつだけ表示できるように今後修正
    @GetMapping("/squares/search/event")
    public String searchSquaresByEventId(@RequestParam("year") String year, @RequestParam("event") String event,
            Model model, Pageable pageable) {

        Long eventId = Long.parseLong(event);

        Page<Square> page = sService.searchPageSquaresByEventId(pageable, eventId);
        model.addAttribute("square_list", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("path", "/squares/search/event?year=" + year + "&event=" + event);
        model.addAttribute("roll", "user");
        List<Integer> years = createYearList();
        model.addAttribute("years", years);
        model.addAttribute("events", eService.getAllEvents());
        model.addAttribute("search", 3);
        model.addAttribute("sentaku", year);
        model.addAttribute("event", eService.getEvent(eventId).getName());
        return "guest_squarelist";
    }

    @GetMapping("/config")
    public String showGuestSugorokuConfigPage(Model model) {
        model.addAttribute("GameConfigForm", new GameConfigForm());
        model.addAttribute("roll", "user");
        List<Event> eventIdList = eService.getAllEvents();
        model.addAttribute("eventIdList", eventIdList);
        return "guest_sugoroku_config";
    }

    @PostMapping("/sugoroku/confirm")
    public String showSugorokuConfirm(GameConfigForm form, Model model) {
        form.addNameAndIcon();
        model.addAttribute("GameConfigForm", form);
        model.addAttribute("roll", "user");
        return "guest_sugoroku_confirm";
    }

    @PostMapping("/sugoroku")
    public String showSugorokuPage(GameConfigForm form, Model model) {
        model.addAttribute("GameConfigForm", form);
        model.addAttribute("roll", "user");
        return "sugoroku";
    }

    private List<Integer> createYearList() {
        List<Integer> years = new ArrayList<>();

        Calendar calender = Calendar.getInstance();
        int latestYear = calender.get(Calendar.YEAR);
        int oldestYear = 2022;

        for (int i = latestYear; i >= oldestYear; i--) {
            years.add(i);
        }

        return years;
    }
}
