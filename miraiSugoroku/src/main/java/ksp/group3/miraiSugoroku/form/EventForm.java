package ksp.group3.miraiSugoroku.form;

import ksp.group3.miraiSugoroku.entity.Event;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class EventForm {
    int nGroups;
    String limitDate;
    String name;

    public Event toEntity() {
        Event e = new Event();

        Date startDate = new Date();
        Date d_limitDate = changeLimitDate();

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
