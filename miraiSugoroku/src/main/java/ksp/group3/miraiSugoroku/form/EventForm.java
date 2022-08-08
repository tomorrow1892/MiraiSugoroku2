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
        Date d_limitDate = getLimitDate();

        e.setName(name);
        e.setStartDate(startDate);
        e.setLimitDate(d_limitDate);
        e.setNGroups(nGroups);

        return e;
    }

    public Date getLimitDate() {
        SimpleDateFormat limitFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d_limitDate = null;

        //TODO 例外処理をする
        try {
            d_limitDate = limitFormat.parse(limitDate);
        } catch (Exception exception) {
            ;
        }

        return d_limitDate;
    }
}
