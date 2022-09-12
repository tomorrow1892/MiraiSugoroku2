package ksp.group3.miraiSugoroku.form;

import ksp.group3.miraiSugoroku.entity.Square;
import lombok.Data;

@Data
public class SquareForm {
    String title;
    Long squareEventId;
    String description;
    Long creatorId;
    Long eventId;
    int groupId;
    boolean isApproved;

    public Square toEntity() {
        Square square = new Square();
        square.setTitle(title);
        square.setSquareEventId(squareEventId);
        square.setDescription(description);
        square.setCreatorId(creatorId);
        square.setApproved(false);
        square.setGroupId(groupId);
        square.setEventId(eventId);

        return square;
    }
}
