package ksp.group3.miraiSugoroku.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksp.group3.miraiSugoroku.entity.Player;
import ksp.group3.miraiSugoroku.entity.SquareEvent;
import ksp.group3.miraiSugoroku.repository.SquareEventRepository;
import ksp.group3.miraiSugoroku.repository.SquareRepository;

@Service
public class SquareEventService {
    @Autowired
    SquareRepository sRepo;
    @Autowired
    SquareEventRepository seRepo;
    @Autowired
    PlayerService pService;

    public SquareEvent getSquareEvent(Long squareEventId){
        return seRepo.findById(squareEventId).get();
    }

    public Player doEvent(SquareEvent e, Long playerId){
        Player p;
        switch(e.getActionNumber()){
            case 1:
                p = pService.move(playerId, e.getArgument());
                break;
            case 2:
                p = pService.enableBreak(playerId);
                break;
            case 3:
                p = pService.updatePoints(playerId, e.getArgument());
                break;
            default:
                //　ここが呼ばれるのは不自然
                p = pService.getPlayer(playerId);
                
        }
        return p;
    }
}