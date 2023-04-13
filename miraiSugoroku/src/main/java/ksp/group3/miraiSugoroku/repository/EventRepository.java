package ksp.group3.miraiSugoroku.repository;

import ksp.group3.miraiSugoroku.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByIsApproved(boolean isApproved);

    List<Event> findByLimitDateAfter(Date date);

    List<Event> findByLimitDateBefore(Date date);

    List<Event> findByEventId(Long eventId);

    List<Event> findByStartDateBetween(Date since, Date until);

    Optional<Event> findByUid(Long uid);
}
