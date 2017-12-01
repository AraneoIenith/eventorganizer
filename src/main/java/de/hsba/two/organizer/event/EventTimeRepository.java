package de.hsba.two.organizer.event;

import de.hsba.two.organizer.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface EventTimeRepository extends JpaRepository<EventTime, Long> {

    @Query("select u from User u where u.username = :username")
    User findCurrentUser(@Param("username") String username);


}
