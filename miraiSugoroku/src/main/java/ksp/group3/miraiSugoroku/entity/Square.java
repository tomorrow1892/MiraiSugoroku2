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
    Long squareId;
    String title;
    Long squareEventId;
    Long eventId;
    int groupId;
    String description;
    Long creatorId;
    int squareEffect; // マスの効果が良い（1）か悪い（2）かを返す
    boolean isApproved;
    @Lob
    String picture;
}
