package ksp.group3.miraiSugoroku.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long playerId; // playerのID
    Long sugorokuId; // playerが参加するすごろくのID
    String icon; // アイコンの画像のパス情報
    String name; // playerの名前
    int order; // playerがサイコロを振る順番
    int points; // playerのスコア
    int position; // playerの現在位置（何番目のマスにいるかを返す）
    Boolean isGoaled; // playerがすでにゴールしていたらTrueを返す
    Boolean isBreak; // playerが休み状態であるときTrueを返す

}