package de.hsba.two.organizer.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface UserRepository extends JpaRepository<User, String> {
    @Query("select u from User u where u.username = :username")
    User findByName(@Param("username") String username);

    @Query("select password from User u where u.username = :username")
    String findPassword(@Param("username") String username);

    @Query("update User u set u.password = :passwordnew where u.username = :username")
    @Modifying
    void changePassword(@Param("username") String username, @Param("passwordnew") String passwordnew);

}

