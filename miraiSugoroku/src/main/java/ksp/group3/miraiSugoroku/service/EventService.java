package ksp.group3.miraiSugoroku.service;

import ksp.group3.miraiSugoroku.entity.Event;
import ksp.group3.miraiSugoroku.exception.MiraiSugorokuException;
import ksp.group3.miraiSugoroku.form.EventForm;
import ksp.group3.miraiSugoroku.repository.EventRepository;
import ksp.group3.miraiSugoroku.security.User;
import ksp.group3.miraiSugoroku.security.UserForm;
import ksp.group3.miraiSugoroku.security.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

@Service
public class EventService {
    @Autowired
    EventRepository eRepo;

    @Autowired
    UserService userService;

    @Autowired
    CreatorService creatorService;

    @Transactional
    public Event createEvent(EventForm eventForm) {
        Event e = new Event();
        Date startDate = new Date();
        SimpleDateFormat limitFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d_limitDate = null;
        try {
            d_limitDate = limitFormat.parse(eventForm.getLimitDate());
        } catch (Exception exception) {
            ;
        }
        e.setName(eventForm.getName());
        long timeInMilliSeconds = startDate.getTime();
        java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);
        e.setStartDate(date1);
        timeInMilliSeconds = d_limitDate.getTime();
        java.sql.Date date2 = new java.sql.Date(timeInMilliSeconds);
        e.setLimitDate(date2);
        e.setNGroups(eventForm.getNGroups());
        e.setApproved(true);
        e.setNMembers(0);

        //サブ管理者ユーザを作成
        UserForm userForm = new UserForm(eventForm.getPassword(),eventForm.getSubadminName());
        User user = userService.createUser(userForm);
        
        //サブ管理者ユーザのidをセット
        e.setUid(user.getUid());

        Event createdEvent =  eRepo.save(e);
        Long eventId = createdEvent.getEventId();
        
        creatorService.createSquareCreators(eventId, eventForm.getNMembers());
        
        
        return createdEvent;
    }

    public Event getEvent(long eventId) {
        Event e = eRepo.findById(eventId)
                .orElseThrow(() -> new MiraiSugorokuException(MiraiSugorokuException.ERROR,
                        eventId + ": No such Event"));

        return e;
    }
    public Event getEventByUid(Long uid){
        Event e = eRepo.findByUid(uid)
        .orElseThrow(() -> new MiraiSugorokuException(MiraiSugorokuException.ERROR, uid+ "No such Event"));
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