package ksp.group3.miraiSugoroku.form;

import java.util.ArrayList;

import ksp.group3.miraiSugoroku.entity.Player;
import ksp.group3.miraiSugoroku.entity.Square;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SugorokuInfoDto {

    int nPlayers; // プレイイヤーの数
    ArrayList<Player> players; // プレイヤーのリスト
    ArrayList<Square> squares; // マスのリスト（順番はsequenceに従った）
    Player nowPlayer; // 現在のターンのプレイヤー

}
