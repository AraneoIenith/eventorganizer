package de.hsba.two.organizer.event;

import de.hsba.two.organizer.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface EventTimeRepository extends JpaRepository<EventTime, Long> {

    @Query("select e from EventTime e where :currentuser member e.participants")
    List<EventTime> findUserEventTimes(@Param("currentuser") User currentuser);
}
