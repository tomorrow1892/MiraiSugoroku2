package ksp.group3.miraiSugoroku.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksp.group3.miraiSugoroku.entity.Board;
import ksp.group3.miraiSugoroku.entity.Player;
import ksp.group3.miraiSugoroku.entity.Square;
import ksp.group3.miraiSugoroku.entity.SquareCreator;
import ksp.group3.miraiSugoroku.entity.SquareEvent;
import ksp.group3.miraiSugoroku.entity.Sugoroku;
import ksp.group3.miraiSugoroku.form.GameConfigForm;
import ksp.group3.miraiSugoroku.form.SugorokuInfoDto;
import ksp.group3.miraiSugoroku.repository.BoardRepository;
import ksp.group3.miraiSugoroku.repository.CreatorRepository;
import ksp.group3.miraiSugoroku.repository.PlayerRepository;
import ksp.group3.miraiSugoroku.repository.SquareEventRepository;
import ksp.group3.miraiSugoroku.repository.SquareRepository;
import ksp.group3.miraiSugoroku.repository.SugorokuRepository;

@Service
public class GameService {

    int minusRate = 50; //--- すごろくにどれだけマイナスなマスを選択するべきか
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
    @Autowired
    CreatorRepository cRepo;
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
    public Sugoroku createSugoroku(GameConfigForm form, boolean withGroupSquares, Long creatorId) {
        Sugoroku g = form.toSugorokuEntity();
        Sugoroku game = sgRepo.save(g);
        Long gameId = game.getSugorokuId();
        pService.createPlayers(form, gameId);

        List<Square> goodSquares;
        List<Square> badSquares;

        //--- ゲストログインの時、グループ選択を強制的に不可能にする
        if (creatorId < 0) {
            withGroupSquares = false;
        }

        if (withGroupSquares) { //--- グループ選択時のマス選択戦略
            SquareCreator creator = cRepo.findById(creatorId).get();
            Long event = creator.getEventId();
            int group = creator.getGroup();
            goodSquares = sqRepo.findByEventIdAndGroupIdAndSquareEffectAndIsApproved(event, group, 1, true);
            badSquares = sqRepo.findByEventIdAndGroupIdAndSquareEffectAndIsApproved(event, group, -1, true);
            // neutralSquares =
            // sqRepo.findByEventIdAndGroupIdAndSquareEffectAndIsApproved(event, group, 0,
            // true);
        } else {
            goodSquares = sqRepo.findBySquareEffectAndIsApproved(1, true);
            badSquares = sqRepo.findBySquareEffectAndIsApproved(-1, true);
            // neutralSquares = sqRepo.findBySquareEffectAndIsApproved(0, true);
        }

        //--- マス生成ロジック
        int length = game.getLength();
        int nBadSquares = length * minusRate / 100;
        //-- * マイナスマスが規定数に満たない場合、合計のマイナスマスを設定する
        if (badSquares.size() < nBadSquares) {
            nBadSquares = badSquares.size();
        }
        int nGoodSquares = length - nBadSquares;
        if (goodSquares.size() < nGoodSquares) {
            nGoodSquares = goodSquares.size();
            nBadSquares = length - nGoodSquares;
            if (badSquares.size() < nBadSquares) {
                //--- * throw error
                //--- * 登録されているマス数が必要なマス数に足りていません
            }
        }
        List<Square> squares = takeAtRandom(goodSquares, nGoodSquares, new Random());
        List<Square> bSquares = takeAtRandom(badSquares, nBadSquares, new Random());

        squares.addAll(bSquares);
        Collections.shuffle(squares);
        int i = 0;
        for (Square square : squares) {
            Board board = new Board(null, gameId, square.getSquareId(), i + 1);
            bRepo.save(board);
            i++;
        }

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
        if (player.getIsGoaled()) {
            advanceTurn(sugorokuId);
            return player;
        }
        Long playerId = player.getPlayerId();
        int position = player.getPosition();
        List<Board> boards = bRepo.findBySugorokuIdAndSequence(sugorokuId, position);
        if (boards.size() != 1) {
            // exceptionを返す
        }
        Square square = sqRepo.findById(boards.get(0).getSquareId()).get();
        SquareEvent squareEvent = seRepo.findById(square.getSquareEventId()).get();
        Player p = seService.doEvent(squareEvent, playerId);
        advanceTurn(sugorokuId);
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

    public Player advanceTurn(Long sugorokuId) {
        Sugoroku game = sgRepo.findById(sugorokuId).get();
        int turn = game.getNowPlayer();
        int nPlayer = game.getNPlayers();
        //全員がゴールしていた場合，何もせずnullを返す
        SugorokuInfoDto dto = getSugorokuInfo(sugorokuId);
        int goalCount=0;
        for (Player p : dto.getPlayers()) {
            if(p.getIsGoaled()) goalCount++;
        }
        if(goalCount == nPlayer) return null;
        boolean flag = false;
        Player next;
        do {
            if (turn == nPlayer) {
                turn = 1;
            } else {
                turn = turn + 1;
            }
            List<Player> nexts = pRepo.findBySugorokuIdAndOrder(sugorokuId, turn);
            if (nexts.size() != 1) {
                // Exception
            }
            next = nexts.get(0);
            flag = next.getIsBreak() || next.getIsGoaled();
            pService.disableBreak(next.getPlayerId());
        } while (flag);
        game.setNowPlayer(turn);
        sgRepo.save(game);
        return next;

    }

    // ------ utility
    public static List<Square> takeAtRandom(Collection<Square> list, int n, Random random) {
        if (list == null) {
            throw new IllegalArgumentException("引数 list の値が null です。");
        }
        if (n > list.size()) {
            throw new IllegalArgumentException(
                    String.format("引数 n の値 %d が引数 list のサイズ %d より大きいです。",
                            n, list.size()));
        }
        if (random == null) {
            throw new IllegalArgumentException("引数 random の値が null です。");
        }

        final List<Square> taken = new ArrayList<>(n); // ランダムに選択された要素を持たせるリスト

        final List<Square> remaining = new ArrayList<>(list); // 残っている要素のリスト
        for (int i = 0; i < n; i++) { // n 回繰り返す。
            final int remainingCount = remaining.size(); // 残っている要素の数
            final int index = random.nextInt(remainingCount); // ランダムに選択されたインデックス

            final Square element = remaining.get(index); // ランダムに選択された要素
            taken.add(element); // ランダムに選択された要素のリストの末尾にランダムに選択された要素を追加する。

            final int lastIndex = remainingCount - 1; // 残っている要素のリストの末尾のインデックス
            final Square lastElement = remaining.remove(lastIndex); // 残っている要素のリストから末尾を削除する。
            if (index < lastIndex) { // ランダムに選択された要素が末尾以外なら…
                remaining.set(index, lastElement); // それを末尾の要素で置換する。
            }
        }

        return taken;
    }

}