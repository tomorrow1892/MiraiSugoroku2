package ksp.group3.miraiSugoroku.form;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UpdateSquareCreatorForm {
    @Length(max=10)
    String nickname;
    int selectedGroup;
}
