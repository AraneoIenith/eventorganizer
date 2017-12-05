package de.hsba.two.organizer.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

    @Query("select e from Event e where e.owner = :currentuser")
    Collection<Event> findByOwner(@Param("currentuser") String currentuser);

}
