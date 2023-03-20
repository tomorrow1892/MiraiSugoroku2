package ksp.group3.miraiSugoroku.form;

import org.hibernate.validator.constraints.Length;

import ksp.group3.miraiSugoroku.entity.Square;
import lombok.Data;

@Data
public class SquareForm {
    @Length(max=28)
    String title;
    Long squareEventId;
    String description;
    Long creatorId;
    String creatorName;
    Long eventId;
    int groupId;
    boolean isApproved;
    String picture;

    public Square toEntity() {
        Square square = new Square();
        square.setTitle(title);
        square.setSquareEventId(squareEventId);
        square.setDescription(description);
        square.setCreatorId(creatorId);
        square.setNickName(creatorName);
        square.setApproved(false);
        square.setGroupId(groupId);
        square.setEventId(eventId);
        square.setPicture(picture);
        square.setSquareEffect(determineKindOfSquare(squareEventId));

        return square;
    }

    public int determineKindOfSquare(Long squareEventId) {
        int kind = 0;
        if (squareEventId >= 1 && squareEventId <= 6) {
            kind = 1;
        } else if (squareEventId >= 7 && squareEventId <= 13) {
            kind = -1;
        } else if (squareEventId >= 15 && squareEventId <= 19) {
            kind = 1;
        } else if (squareEventId >= 20 && squareEventId <= 24) {
            kind = -1;
        }

        return kind;
    }
}
