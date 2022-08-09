package ksp.group3.miraiSugoroku.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SquareEvent {
    @Id
    Long squareEventID;
    int actionNumber;
    int argument;
    String eventTitle;

}
