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

    String subadminName; // サブ管理者名
    @Size(max = 16)
    @Pattern(regexp = "[0-9a-zA-Z]+")

    String password; // パスワード

<<<<<<< HEAD
        e.setName(name);
        long timeInMilliSeconds = startDate.getTime();
        java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);
        e.setStartDate(date1);
        timeInMilliSeconds = d_limitDate.getTime();
        java.sql.Date date2 = new java.sql.Date(timeInMilliSeconds);
        e.setLimitDate(date2);
        e.setNGroups(nGroups);
        e.setApproved(true);
        e.setNMembers(0);

        return e;
    }
=======
    @Size(max = 16)
    @Pattern(regexp = "[0-9a-zA-Z]+")
>>>>>>> 5b99af0db746fe4fe2744e90e47a40889ba8b76d

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
