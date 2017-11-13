package de.hsba.two.organizer.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface UserRepository extends JpaRepository<User, Long>{
    @Query("select u from User u where u.name = :name")
    User findByName(@Param("name") String name);
}
