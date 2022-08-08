package ksp.group3.miraiSugoroku.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long boardId; // 盤面とマス情報をつなげる情報のID
    Long sugorokuId; // つなげるすごろくのID
    Long squareId; // つなげるマスのID
    int sequence; // 盤面の何番目に配置するかの値

}
