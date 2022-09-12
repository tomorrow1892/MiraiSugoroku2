package ksp.group3.miraiSugoroku.form;

import ksp.group3.miraiSugoroku.entity.SquareCreator;
import lombok.Data;

@Data
public class SquareCreatorForm {
    String loginId;
    String name;
    String nickname;
    long eventId;
    int group;

    public SquareCreator toEntity() {
        SquareCreator sc = new SquareCreator();

        sc.setLoginId(loginId);
        sc.setName(name);
        sc.setNickname(nickname);
        sc.setEventId(eventId);
        sc.setGroup(group);
        sc.setPermitted(true);

        return sc;
    }
}
