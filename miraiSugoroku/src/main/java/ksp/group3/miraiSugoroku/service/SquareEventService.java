package ksp.group3.miraiSugoroku.service;

import java.util.List;

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

    public SquareEvent getSquareEvent(Long squareEventId) {
        return seRepo.findById(squareEventId).get();
    }

    public List<SquareEvent> getAllSquareEvent() {
        List<SquareEvent> seList = seRepo.findAll();
        return seList;
    }

    // マス作成に必要なマスイベントのみを返す
    public List<SquareEvent> getSquareEventForCreate() {
        List<SquareEvent> selist = getAllSquareEvent();
        selist.remove(13);
        selist.remove(11);
        selist.remove(10);
        selist.remove(9);
        selist.remove(5);
        selist.remove(4);
        selist.remove(3);
        return selist;
    }

    public Player doEvent(SquareEvent e, Long playerId) {
        Player p;
        switch (e.getActionNumber()) {
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
                // ここが呼ばれるのは不自然
                p = pService.getPlayer(playerId);

        }
        return p;
    }

    @PostConstruct
    public void registerSquareEvent() {
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
        se.setEventTitle("1マス進む");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 2);
        se.setActionNumber(1);
        se.setArgument(2);
        se.setEventTitle("2マス進む");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 3);
        se.setActionNumber(1);
        se.setArgument(3);
        se.setEventTitle("3マス進む");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 4);
        se.setActionNumber(1);
        se.setArgument(4);
        se.setEventTitle("4マス進む");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 5);
        se.setActionNumber(1);
        se.setArgument(5);
        se.setEventTitle("5マス進む");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 6);
        se.setActionNumber(1);
        se.setArgument(6);
        se.setEventTitle("6マス進む");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 7);
        se.setActionNumber(1);
        se.setArgument(-1);
        se.setEventTitle("1マス戻る");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 8);
        se.setActionNumber(1);
        se.setArgument(-2);
        se.setEventTitle("2マス戻る");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 9);
        se.setActionNumber(1);
        se.setArgument(-3);
        se.setEventTitle("3マス戻る");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 10);
        se.setActionNumber(1);
        se.setArgument(-4);
        se.setEventTitle("4マス戻る");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 11);
        se.setActionNumber(1);
        se.setArgument(-5);
        se.setEventTitle("5マス戻る");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 12);
        se.setActionNumber(1);
        se.setArgument(-6);
        se.setEventTitle("6マス戻る");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 13);
        se.setActionNumber(2);
        se.setEventTitle("一回休み");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 15);
        se.setActionNumber(3);
        se.setArgument(100);
        se.setEventTitle("+100ポイント");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 16);
        se.setActionNumber(3);
        se.setArgument(200);
        se.setEventTitle("+200ポイント");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 17);
        se.setActionNumber(3);
        se.setArgument(300);
        se.setEventTitle("+300ポイント");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 18);
        se.setActionNumber(3);
        se.setArgument(400);
        se.setEventTitle("+400ポイント");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 19);
        se.setActionNumber(3);
        se.setArgument(500);
        se.setEventTitle("+500ポイント");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 20);
        se.setActionNumber(3);
        se.setArgument(-100);
        se.setEventTitle("-100ポイント");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 21);
        se.setActionNumber(3);
        se.setArgument(-200);
        se.setEventTitle("-200ポイント");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 22);
        se.setActionNumber(3);
        se.setArgument(-300);
        se.setEventTitle("-300ポイント");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 23);
        se.setActionNumber(3);
        se.setArgument(-400);
        se.setEventTitle("-400ポイント");
        seRepo.save(se);

        se = new SquareEvent();
        se.setSquareEventID((long) 24);
        se.setActionNumber(3);
        se.setArgument(-500);
        se.setEventTitle("-500ポイント");
        seRepo.save(se);

    }
}