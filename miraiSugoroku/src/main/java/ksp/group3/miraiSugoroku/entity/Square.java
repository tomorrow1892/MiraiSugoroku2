package ksp.group3.miraiSugoroku.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Square {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long squareId;//マスID
    String title;//マスのタイトル
    Long squareEventId;//マスイベントIDf
    Long eventId;//イベントID
    int groupId;//グループID
    String description;//マスの詳細説明
    Long creatorId;//作成者ID
    String nickName;//作成者ニックネーム
    int squareEffect; // マスの効果が良い（1）か悪い（-1）かを返す
    boolean isApproved;//承認されているか
    String link;//マス詳細画面でマスに紐づいたリンク
    @Lob
    String picture;//画像(文字列に変換されたもの)
}
