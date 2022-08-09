package ksp.group3.miraiSugoroku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {
    
    @GetMapping("/game")
    public String showIndexPage() {
        return "sugorokuGame";
    }
}
