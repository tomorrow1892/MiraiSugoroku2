package ksp.group3.miraiSugoroku.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksp.group3.miraiSugoroku.entity.Player;
import ksp.group3.miraiSugoroku.entity.Sugoroku;
import ksp.group3.miraiSugoroku.form.GameConfigForm;
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
            System.out.println("movetest");
        } else if (position >= length + 1) {
            player.setPosition(length + 1);
            player.setIsGoaled(true);
            System.out.println("movetest");
        } else {
            player.setPosition(position);
            System.out.println("movetest");
        }
        System.out.println("movetest2");
    
        System.out.println(player);

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
     * プレイヤーの休みを解除する
     * 
     * @param playerId
     * @return
     */
    public Player disableBreak(Long playerId) {
        Player player = pRepo.findById(playerId).get();
        player.setIsBreak(false);
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

    /**
     * 指定されたすごろくの現在のプレイヤーの順番を返す
     * 
     * @param sugorokuId
     * @return
     */
    public Player getTurnPlayer(Long sugorokuId) {
        Sugoroku game = sRepo.findById(sugorokuId).get();
        int turn = game.getNowPlayer();
        List<Player> p = pRepo.findBySugorokuIdAndOrder(sugorokuId, turn);
        if (p.size() != 1) {
            // exceptionを返す
        }
        Player player = p.get(0);
        return player;
    }

    /**
     * GameConfigFormからプレイヤーを生成する
     * 
     * @param form
     * @param sugorokuId プレイヤーの作成元となるすごろくのID
     * @return
     */
    public List<Player> createPlayers(GameConfigForm form, Long sugorokuId) {
        int nPlayers = form.getNPlayers();
        List<String> names = form.getNames();
        if (nPlayers == 0) {
            nPlayers = names.size();
        }

        List<String> icons = form.getIcons();
        List<Player> players = new ArrayList<Player>();
        for (int i = 0; i < nPlayers; i++) {
            Player player = new Player(null, sugorokuId, icons.get(i), names.get(i), i + 1, 0, 0, false, false);
            players.add(player);
            pRepo.save(player);
        }
        return players;
    }

    /**
     * IDからプレイヤーを返す
     * 
     * @param playerId
     * @return
     */
    public Player getPlayer(Long playerId) {
        Player player = pRepo.findById(playerId).get();
        return player;
    }

}