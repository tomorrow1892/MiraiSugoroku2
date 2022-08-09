package ksp.group3.miraiSugoroku.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksp.group3.miraiSugoroku.entity.Board;
import ksp.group3.miraiSugoroku.entity.Player;
import ksp.group3.miraiSugoroku.entity.Square;
import ksp.group3.miraiSugoroku.entity.SquareEvent;
import ksp.group3.miraiSugoroku.entity.Sugoroku;
import ksp.group3.miraiSugoroku.form.GameConfigForm;
import ksp.group3.miraiSugoroku.repository.BoardRepository;
import ksp.group3.miraiSugoroku.repository.PlayerRepository;
import ksp.group3.miraiSugoroku.repository.SquareEventRepository;
import ksp.group3.miraiSugoroku.repository.SquareRepository;
import ksp.group3.miraiSugoroku.repository.SugorokuRepository;

@Service
public class GameService {

    // Repository
    @Autowired
    PlayerRepository pRepo;
    @Autowired
    SugorokuRepository sgRepo;
    @Autowired
    BoardRepository bRepo;
    @Autowired
    SquareRepository sqRepo;
    @Autowired
    SquareEventRepository seRepo;
    // Service
    @Autowired
    PlayerService pService;
    @Autowired
    SquareEventService seService;

    /**
     * すごろくを作成する
     * 
     * @param form
     * @return
     */
    public Sugoroku createSugoroku(GameConfigForm form) {
        Sugoroku g = form.toSugorokuEntity();
        Sugoroku game = sgRepo.save(g);
        Long gameId = game.getSugorokuId();
        pService.createPlayers(form, gameId);
        // boardを作成する
        return game;
    }

    /**
     * さいころの出目に応じて現在のプレイヤーを移動させる
     * 
     * @param sugorokuId
     * @param n          出目の値
     */
    public Player moveByDice(Long sugorokuId, int n) {
        Sugoroku game = sgRepo.findById(sugorokuId).get();
        int turn = game.getNowPlayer();
        List<Player> players = pRepo.findBySugorokuIdAndOrder(sugorokuId, turn);
        if (players.size() != 1) {
            // exceptionを返す
        }
        Player player = players.get(0);
        pService.move(player.getPlayerId(), n);
        return player;
    }

    /**
     * すごろくのマスイベントを実行する
     * 
     * @param sugorokuId
     * @return
     */
    public Player executeSquareEvent(Long sugorokuId) {
        Sugoroku game = sgRepo.findById(sugorokuId).get();
        int turn = game.getNowPlayer();
        List<Player> players = pRepo.findBySugorokuIdAndOrder(sugorokuId, turn);
        if (players.size() != 1) {
            // exceptionを返す
        }
        Player player = players.get(0);
        Long playerId = player.getPlayerId();
        int position = player.getPosition();
        List<Board> boards = bRepo.findBySugorokuIdAndSequence(sugorokuId, position);
        if (boards.size() != 1) {
            // exceptionを返す
        }
        Square square = sqRepo.findById(boards.get(0).getSquareId()).get();
        SquareEvent squareEvent = seRepo.findById(square.getSquareEventId()).get();
        Player p = seService.doEvent(squareEvent, playerId);
        return p;
    }

}
