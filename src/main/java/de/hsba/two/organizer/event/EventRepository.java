package de.hsba.two.organizer.event;

import de.hsba.two.organizer.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

    @Query("select e from Event e where e.owner = :currentuser")
    Collection<Event> findByOwner(@Param("currentuser") User currentuser);

    @Query("update Event e set e.name = :namenew where e.id = :id")
    @Modifying
    void  changeEventName(@Param ("id") Long id, @Param("namenew") String namenew);

    @Query("update Event e set e.category = :categorynew where e.id = :id")
    @Modifying
    void  changeEventCategory(@Param ("id") Long id, @Param("categorynew") String categorynew);

}

