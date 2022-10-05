package ksp.group3.miraiSugoroku.service;

import ksp.group3.miraiSugoroku.entity.Event;
import ksp.group3.miraiSugoroku.exception.MiraiSugorokuException;
import ksp.group3.miraiSugoroku.form.EventForm;
import ksp.group3.miraiSugoroku.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        long timeInMilliSeconds = eventForm.changeLimitDate().getTime();
        java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);
        e.setLimitDate(date1);
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
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return eRepo.findByLimitDateAfter(cal.getTime());
    }

    public List<Event> getNotActiveEvents(Date day) {
        return eRepo.findByLimitDateBefore(day);
    }

    public List<Event> getAllEvents() {
        List<Event> allEvents = new ArrayList<>();
        for (Event e : eRepo.findAll()) {
            allEvents.add(e);
        }

        return allEvents;
    }

    public boolean getBoolean(Long eventId) {
        Event e = getEvent(eventId);
        Date d = e.getLimitDate();
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, -1);

        int compare = d.compareTo(cal.getTime());
        if (compare > 0) {
            e.setApproved(true);
        } else {
            e.setApproved(false);
        }

        return e.isApproved();
    }
}