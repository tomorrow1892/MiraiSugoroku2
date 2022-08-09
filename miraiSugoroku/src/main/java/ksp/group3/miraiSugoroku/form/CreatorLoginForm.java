package ksp.group3.miraiSugoroku.form;

import lombok.Data;

@Data
public class CreatorLoginForm {
    int selectedYear = 0;
    Long selectedEventId = null;
    String loginId = null;
}
