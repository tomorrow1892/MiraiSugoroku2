package ksp.group3.miraiSugoroku.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksp.group3.miraiSugoroku.entity.Player;
import ksp.group3.miraiSugoroku.entity.Sugoroku;
import ksp.group3.miraiSugoroku.repository.PlayerRepository;
import ksp.group3.miraiSugoroku.repository.SugorokuRepository;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository pRepo;
    @Autowired
    SugorokuRepository sRepo;

    /**
     * プレイヤーの位置を変更する
     * 
     * @param playerId
     * @param n
     * @return
     */
    public Player move(Long playerId, int n) {
        Player player = pRepo.findById(playerId).get();
        Sugoroku game = sRepo.findById(player.getSugorokuId()).get();
        int length = game.getLength();
        int position = player.getPosition();
        position = position + n;
        if (position < 0) {
            player.setPosition(0);
        } else if (position > length + 1) {
            player.setPosition(length + 1);
        } else {
            player.setPosition(position);
        }
        return pRepo.save(player);
    }

    /**
     * プレイヤーに休みを設定する
     * 
     * @param playerId
     * @return
     */
    public Player enableBreak(Long playerId) {
        Player player = pRepo.findById(playerId).get();
        player.setIsBreak(true);
        return pRepo.save(player);
    }

    /**
     * プレイヤーの得点を更新する
     * 
     * @param playerId
     * @param points   更新する得点の差分
     * @return
     */
    public Player updatePoints(Long playerId, int points) {
        Player player = pRepo.findById(playerId).get();
        int score = player.getPoints();
        score = score + points;
        if (score < 0) {
            player.setPoints(0);
        } else {
            player.setPoints(score);
        }
        return pRepo.save(player);
    }

}
