package de.hsba.two.organizer.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface UserRepository extends JpaRepository<User, String>{
   @Query("select u from User u where u.username = :username")
    User findByName(@Param("username") String username);
}
