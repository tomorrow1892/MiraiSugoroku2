package ksp.group3.miraiSugoroku.service;

import ksp.group3.miraiSugoroku.entity.Event;
import ksp.group3.miraiSugoroku.exception.MiraiSugorokuException;
import ksp.group3.miraiSugoroku.form.EventForm;
import ksp.group3.miraiSugoroku.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EventService {
    @Autowired
    EventRepository eRepo;

    public Event createEvent(EventForm eventForm) {
        Event e = eventForm.toEntity();

        return eRepo.save(e);
    }

    public Event getEvent(long eventId) {
        Event e = eRepo.findById(eventId)
                .orElseThrow(() -> new MiraiSugorokuException(MiraiSugorokuException.ERROR,
                        eventId + ": No such Event"));

        return e;
    }

    public Event updateEvent(long eventId, EventForm eventForm) {
        Event e = getEvent(eventId);

        e.setNGroups(eventForm.getNGroups());
        e.setLimitDate(eventForm.getLimitDate());
        e.setName(eventForm.getName());

        return eRepo.save(e);
    }

    public void deleteEvent(long eventId) {
        eRepo.deleteById(eventId);
    }

    public List<Event> filterEventsByYear(int year) {
        String s_start = String.valueOf(year) + "-01-01";
        String s_until = String.valueOf(year) + "-12-31";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date until = null;

        try {
            start = sdf.parse(s_start);
            until = sdf.parse(s_until);
        } catch (Exception e) {
            ;
        }

        return eRepo.findByStartDateBetween(start, until);
    }

    public List<Event> getActiveEvents(Date day) {
        return eRepo.findByLimitDateAfter(day);
    }
}
