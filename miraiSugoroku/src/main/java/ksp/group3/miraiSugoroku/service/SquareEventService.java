package ksp.group3.miraiSugoroku.service;

import javax.annotation.PostConstruct;

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
            case 0:
                p = pService.getPlayer(playerId);
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

    @PostConstruct
    public void registerSquareEvent(){
        SquareEvent se;

        se = new SquareEvent();
        se.setSquareEventID((long) 14);
        se.setActionNumber(0);
        se.setEventTitle("");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 1);
        se.setActionNumber(1);
        se.setArgument(1);
        se.setEventTitle("1進む");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 2);
        se.setActionNumber(1);
        se.setArgument(2);
        se.setEventTitle("2進む");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 3);
        se.setActionNumber(1);
        se.setArgument(3);
        se.setEventTitle("3進む");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 4);
        se.setActionNumber(1);
        se.setArgument(4);
        se.setEventTitle("4進む");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 5);
        se.setActionNumber(1);
        se.setArgument(5);
        se.setEventTitle("5進む");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 6);
        se.setActionNumber(1);
        se.setArgument(6);
        se.setEventTitle("6進む");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 7);
        se.setActionNumber(1);
        se.setArgument(-1);
        se.setEventTitle("1戻る");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 8);
        se.setActionNumber(1);
        se.setArgument(-2);
        se.setEventTitle("2戻る");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 9);
        se.setActionNumber(1);
        se.setArgument(-3);
        se.setEventTitle("3戻る");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 10);
        se.setActionNumber(1);
        se.setArgument(-4);
        se.setEventTitle("4戻る");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 11);
        se.setActionNumber(1);
        se.setArgument(-5);
        se.setEventTitle("5戻る");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 12);
        se.setActionNumber(1);
        se.setArgument(-6);
        se.setEventTitle("6戻る");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 13);
        se.setActionNumber(2);
        se.setEventTitle("一回休み");
        seRepo.save(se);

    }
}