package ksp.group3.miraiSugoroku.form;

import java.util.ArrayList;
import java.util.List;

import ksp.group3.miraiSugoroku.entity.Player;
import ksp.group3.miraiSugoroku.entity.Sugoroku;
import lombok.Data;

@Data
public class GameConfigForm {

    int nPlayers; // playerの数
    int length; // すごろくの長さ
    ArrayList<String> names; // playerの名前のリスト
    ArrayList<String> icons; // playerが使用するアイコンのパス情報

    public Sugoroku toSugorokuEntity() {
        if (names.size() != icons.size()) {
            // exceptionを返す
        }
        this.nPlayers = names.size();
        int nSquares = 0;
        switch (length) {
            case 0:
                nSquares = 20;
                break;
            case 1:
                nSquares = 35;
                break;
            case 2:
                nSquares = 50;
                break;
            default:
                // exceptionを返す
                break;
        }
        Sugoroku game = new Sugoroku(null, 1, nPlayers, nSquares);
        return game;

    }

    public List<Player> toPlayerEntityList() {

        List<Player> players = new ArrayList<Player>();
        for (int i = 0; i < nPlayers; i++) {
            Player player = new Player(null, null, icons.get(i), names.get(i), i + 1, 0, 0, false, false);
            players.add(player);
        }
        return players;

    }

}