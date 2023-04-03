package ksp.group3.miraiSugoroku.form;

import ksp.group3.miraiSugoroku.entity.Event;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class EventForm {
    int nGroups; //グループ数
    String limitDate;//有効日数
    String name;// イベント名
    String subadminName; //サブ管理者名
    String password; //パスワード

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
