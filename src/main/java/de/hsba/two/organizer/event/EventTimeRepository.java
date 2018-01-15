package de.hsba.two.organizer.event;

import de.hsba.two.organizer.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface EventTimeRepository extends JpaRepository<EventTime, Long> {

    @Query("select e from EventTime e where :currentuser member e.participants")
    List<EventTime> findUserEventTimes(@Param("currentuser") User currentuser);

    @Query("update EventTime e set e.title = :titlenew where e.id = :id")
    @Modifying
    void changeEventtimetitle(@Param("id") Long id, @Param("titlenew") String titlenew);

    @Query("update EventTime e set e.description = :descriptionnew where e.id = :id")
    @Modifying
    void changeEventtimeDescription(@Param("id") Long id, @Param("descriptionnew") String descriptionnew);

    @Query("update EventTime e set e.date = :datenew where e.id = :id")
    @Modifying
    void changeEventtimeDate(@Param("id") Long id, @Param("datenew") String datenew);

    @Query("update EventTime e set e.time = :timenew where e.id = :id")
    @Modifying
    void changeEventtimeTime(@Param("id") Long id, @Param("timenew") String timenew);

    @Query("update EventTime e set e.duration = :durationnew where e.id = :id")
    @Modifying
    void changeEventtimeDuration(@Param("id") Long id, @Param("durationnew") String durationnew);

}
