package ksp.group3.miraiSugoroku.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksp.group3.miraiSugoroku.entity.Board;
import ksp.group3.miraiSugoroku.entity.Player;
import ksp.group3.miraiSugoroku.entity.Square;
import ksp.group3.miraiSugoroku.entity.SquareEvent;
import ksp.group3.miraiSugoroku.entity.Sugoroku;
import ksp.group3.miraiSugoroku.form.GameConfigForm;
import ksp.group3.miraiSugoroku.form.SugorokuInfoDto;
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
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa");
        Sugoroku game = sgRepo.findById(sugorokuId).get();
        int turn = game.getNowPlayer();
        List<Player> players = pRepo.findBySugorokuIdAndOrder(sugorokuId, turn);
        if (players.size() != 1) {
            // exceptionを返す
        }
       
        Player player = players.get(0);
        System.out.println("ddddddddddddddddddddddddd");
        System.out.println(player.getPlayerId());
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

    /**
     * 中断復帰の際にフロントに渡すデータを取得する
     * 
     * @param sugorokuId
     * @return
     */
    public SugorokuInfoDto getSugorokuInfo(Long sugorokuId) {
        Sugoroku game = sgRepo.findById(sugorokuId).get();
        int nPlayers = game.getNPlayers();
        int turn = game.getNowPlayer();
        List<Player> np = pRepo.findBySugorokuIdAndOrder(sugorokuId, turn);
        if (np.size() != 1) {
            // exceptionを投げる
        }
        Player nowPlayer = np.get(0);
        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0; i < nPlayers; i++) {
            List<Player> pls = pRepo.findBySugorokuIdAndOrder(sugorokuId, i + 1);
            if (pls.size() != 1) {
                // throw exception
            }
            players.add(pls.get(0));
        }
        ArrayList<Square> squares = new ArrayList<Square>();
        for (int i = 0; i < game.getLength(); i++) {
            List<Board> bs = bRepo.findBySugorokuIdAndSequence(sugorokuId, i + 1);
            if (bs.size() != 1) {
                // exceptionを投げる
            }
            squares.add(sqRepo.findById(bs.get(0).getSquareId()).get());
        }
        SugorokuInfoDto dto = new SugorokuInfoDto(nPlayers, players, squares, nowPlayer);
        return dto;
    }

}
