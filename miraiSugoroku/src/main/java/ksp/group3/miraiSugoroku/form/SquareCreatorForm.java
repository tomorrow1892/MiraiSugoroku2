package ksp.group3.miraiSugoroku.form;

import ksp.group3.miraiSugoroku.entity.SquareCreator;
import lombok.Data;

@Data
public class SquareCreatorForm {
    String loginId = null;
    String name = null;
    String nickname = null;
    long eventId = 0;
    int group = 0;

    public SquareCreator toEntity() {
        SquareCreator sc = new SquareCreator();

        sc.setLoginID(loginId);
        sc.setName(name);
        sc.setNickname(nickname);
        sc.setEventID(eventId);
        sc.setGroup(group);
        sc.setPermitted(true);

        return sc;
    }
}
