package ksp.group3.miraiSugoroku.form;

import ksp.group3.miraiSugoroku.entity.Event;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class EventForm {
    int nGroups; // グループ数
    String limitDate;// 有効日数
    String name;// イベント名
    int nMembers;// イベント参加者

    @Size(max = 16)
    @Pattern(regexp = "[0-9a-zA-Z]+")
    String subadminName; // サブ管理者名
    
    @Size(max = 16)
    @Pattern(regexp = "[0-9a-zA-Z]+")
    String password; // パスワード

    

    public Date changeLimitDate() {
        SimpleDateFormat limitFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d_limitDate = null;

        // TODO 例外処理をする
        try {
            d_limitDate = limitFormat.parse(limitDate);
        } catch (Exception exception) {
            ;
        }

        return d_limitDate;
    }
}
