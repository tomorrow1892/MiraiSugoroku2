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
public class Sugoroku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long sugorokuId; // すごろくのID
    int nowPlayer; // 現在行動しているplayerの順番
    int nPlayers; // playerの数
    int length; // すごろくが持っているマスの数（ゴール・スタートは除く）

}
