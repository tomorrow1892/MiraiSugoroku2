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
    String p1;
    String i1;
    String p2;
    String i2;
    String p3;
    String i3;
    String p4;
    String i4;
    String p5;
    String i5;
    String p6;
    String i6;
    ArrayList<String> names; // playerの名前のリスト
    ArrayList<String> icons; // playerが使用するアイコンのパス情報

    public void addNameAndIcon() {
        if (this.p1 != null && this.i1 != null) {
            this.names.add(p1);
            this.icons.add(i1);
        }
        if (this.p2 != null && this.i2 != null) {
            this.names.add(p2);
            this.icons.add(i2);
        }
        if (this.p3 != null && this.i3 != null) {
            this.names.add(p3);
            this.icons.add(i3);
        }
        if (this.p4 != null && this.i4 != null) {
            this.names.add(p4);
            this.icons.add(i4);
        }
        if (this.p5 != null && this.i5 != null) {
            this.names.add(p5);
            this.icons.add(i5);
        }
        if (this.p6 != null && this.i6 != null) {
            this.names.add(p6);
            this.icons.add(i6);
        }
        this.nPlayers = names.size();
    }

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