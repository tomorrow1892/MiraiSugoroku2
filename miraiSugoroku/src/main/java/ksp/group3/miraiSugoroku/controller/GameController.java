package ksp.group3.miraiSugoroku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GameController {
    
    @GetMapping("/game/{sugorokuId}")
    public String showSugorokuPage(@PathVariable String sugorokuId,Model model) {
        Long sId = Long.parseLong(sugorokuId);
        model.addAttribute("sugorokuId",sId);
        return "sugorokuGame";
    }
}
