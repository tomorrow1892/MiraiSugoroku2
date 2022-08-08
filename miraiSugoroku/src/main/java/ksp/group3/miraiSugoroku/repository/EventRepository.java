package ksp.group3.miraiSugoroku.repository;

import ksp.group3.miraiSugoroku.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByIsApproved(boolean isApproved);

    List<Event> findByLimitDateAfter(Date date);

    List<Event> findByStartDateBetween(Date since, Date until);
}
