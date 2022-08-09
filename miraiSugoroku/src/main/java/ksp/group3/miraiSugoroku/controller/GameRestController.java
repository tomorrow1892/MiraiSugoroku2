package ksp.group3.miraiSugoroku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ksp.group3.miraiSugoroku.entity.Player;
import ksp.group3.miraiSugoroku.service.GameService;

@RestController
public class GameRestController {
    @Autowired
    GameService gameService;

    ObjectMapper objectMapper = new ObjectMapper();
    @GetMapping("/api/diceRoll")
    String diceRoll(@RequestParam String suzi,@RequestParam String sugorokuId) throws JsonProcessingException{
        System.out.println("出目:"+suzi);
        System.out.println("すごろくID:"+ sugorokuId);
        //return objectMapper.writeValueAsString(new Player( null, null, "aaa!", "さんだろう", 1, 0, 1, true, false));
         return objectMapper.writeValueAsString(gameService.moveByDice(Long.parseLong(sugorokuId), Integer.parseInt(suzi)));
    }

    @GetMapping("/api/doEvent")
    String doEvent() throws JsonProcessingException{

        //Player plyaer = gameservice.doEvent(sugorokuid);

        return objectMapper.writeValueAsString( new Player( null, null, "aaa!", "さんだろう", 1, 200, 1, false, false));
    }
}