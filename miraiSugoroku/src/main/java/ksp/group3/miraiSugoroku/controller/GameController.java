package ksp.group3.miraiSugoroku.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ksp.group3.miraiSugoroku.entity.Player;
import ksp.group3.miraiSugoroku.entity.Square;
import ksp.group3.miraiSugoroku.entity.SquareCreator;
import ksp.group3.miraiSugoroku.entity.Sugoroku;
import ksp.group3.miraiSugoroku.form.GameConfigForm;
import ksp.group3.miraiSugoroku.form.SugorokuInfoDto;
import ksp.group3.miraiSugoroku.service.GameService;


@Controller
public class GameController {
    @Autowired
    GameService gameService;
    
    @GetMapping("/game/{sugorokuId}")
    public String showSugorokuPage(@PathVariable String sugorokuId,Model model) {
        Long sId = Long.parseLong(sugorokuId);
        model.addAttribute("sugorokuId",sId);
        SugorokuInfoDto sugorokuInfoDto = gameService.getSugorokuInfo(sId);
        ArrayList<Player> playerList = sugorokuInfoDto.getPlayers();
        ArrayList<Square> squareList = sugorokuInfoDto.getSquares();
        Player nowPlayer = sugorokuInfoDto.getNowPlayer();
        model.addAttribute("nPlayers",sugorokuInfoDto.getNPlayers());
        model.addAttribute("playerList",sugorokuInfoDto.getPlayers());
        model.addAttribute("squareList",sugorokuInfoDto.getSquares());
        model.addAttribute("nowPlayer",sugorokuInfoDto.getNowPlayer());
        model.addAttribute("sugorokuId",sId);

        return "sugorokuGame";
    }

    @PostMapping("/game/createSugoroku")
    public String createSugoroku(GameConfigForm form,Model model) {
        //TODO: process POST request
        Sugoroku sugoroku = gameService.createSugoroku(form, false, Long.parseLong("-1"));
        Long sugorokuId = sugoroku.getSugorokuId();
        return "redirect:/game/"+sugorokuId;
    }

    @GetMapping("/game/config")
    public String configSugorokuTest(Model model){
        model.addAttribute("gameConfigForm",new GameConfigForm());
        return "gameConfigTest";
    }
}
