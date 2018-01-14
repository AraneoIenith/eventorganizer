package de.hsba.two.organizer.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    //Suche nach der Personalnummer
    @Query("select u from User u where u.username = :username")
    User findByName(@Param("username") String username);

    //Suche nach einer vom Benutzer eingebenen Personalnummer
    @Query("select username from User u where u.username = :usernamenew")
    String findUsername(@Param("usernamenew") String usernamenew);

    //Suche nach Passwort eines Users
    @Query("select password from User u where u.username = :username")
    String findPassword(@Param("username") String username);

    //Suche nach dem Status eines Nutzers, d.h. ob dieser aktiv oder inaktiv ist (für das Deaktivieren relevant)
    @Query("select active from User u where u.username = :username")
    String findStatus(@Param("username") String username);

    //Update Passwort - anhand der Personalnummer wird der richtige Benutzer gefunden
    @Query("update User u set u.password = :passwordnew where u.username = :username")
    @Modifying
    void changePassword(@Param("username") String username, @Param("passwordnew") String passwordnew);

    //Update Personalnummer
    @Query("update User u set u.username = :usernamenew where u.username = :username")
    @Modifying
    void changeUsername(@Param("username") String username, @Param("usernamenew") String usernamenew);

    //Update Vorname
    @Query("update User u set u.firstname = :firstnamenew where u.username = :username")
    @Modifying
    void changeFirstname(@Param("username") String username, @Param("firstnamenew") String firstnamenew);

    //Update Rolle
    @Query("update User u set u.role = :rolenew where u.username = :username")
    @Modifying
    void changeRole(@Param("username") String username, @Param("rolenew") String rolenew);

    //Update Status auf inaktiv
    @Query("update User u set u.active = false where u.username = :username")
    @Modifying
    void changeDeactivate(@Param("username") String username);

    //Update Staus auf akiv
    @Query("update User u set u.active = true where u.username = :username")
    @Modifying
    void changeActive(@Param("username") String username);

}

