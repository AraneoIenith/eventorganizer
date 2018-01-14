package de.hsba.two.organizer.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        createUser("00000", "Anna", "00000", "HR", true);
        createUser("11111", "Berta", "11111", "ORGANIZER", true);
        createUser("22222", "Conny", "22222", "USER", true);
        createUser("33333", "Dora", "33333", "ORGANIZER", true);
    }

    //Speichert einen User
    //Rückgabe eines Strings für einen URL Parameter je nachdem, ob der Nutzer bereits existiert oder nicht
    public String createUser(String username, String firstname, String password, String role, boolean active) {
        if (!matchUser(username)){
            userRepository.save(new User(username, firstname, passwordEncoder.encode(password), role, true));
            return "usercreated";
        }
        else {
            return "userexist";
        }
    }

    //Das User Objekt des aktuellen Nutzers
    public User getUserObj() {
        String currentuser = getUserName();
        return userRepository.findByName(currentuser);
    }

    //Der Username (Personalnummer) des aktuellen Nutzers
    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    //Das User Objekt eines beliebigen Users
    public User getUser(String username) {
        return userRepository.findByName(username);
    }

    //Alle in der DB gespeicherten Nutzer
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //Neues Passwort wird über den Controller geholt und in die DB geschrieben
    public void changePassword(String username, String passwordnew) {
        userRepository.changePassword(username, passwordEncoder.encode(passwordnew));
    }

    //Suche nach einem Passwort von einem Nutzer
    public String getPassword(String username) {
        return userRepository.findPassword(username);
    }

    //Suche nach Stats eines Nutzers
    public String getStatus(String username) {return userRepository.findStatus(username);}

    public boolean EncPass(String passwordold, String password) {
        return passwordEncoder.matches(passwordold, password);
    }

    //Änderung der Personalnummer: neue Personalnr. wird vom Controller übergeben und in die DB gespeichert
    public void changeUsername(String username, String usernamenew) {
        userRepository.changeUsername(username, usernamenew);
    }

    //Änderung des Vornamens
    public void changefirstname (String username, String firstnamenew) {
        userRepository.changeFirstname(username, firstnamenew);
    }


    //Änderung der Rolle
    public void changeRole (String username, String rolenew) {
        userRepository.changeRole(username, rolenew);
    }

    //Überprüfung, ob die eingegebene Personalnummer schon vergeben ist, d.h. bereits in der DB existiert.
    //Hierfür wird die neue Personalnr. in der DB gesucht. Falls diese nicht vergeben ist, ist der Rückgabewert null.
    public boolean matchUser(String usernamenew) {
        Object username = userRepository.findUsername(usernamenew);

        if (username == null)
            return false;
        else return true;
    }

    //Änderung des Status auf inaktiv
    public void changeToDeactive (String username){
       userRepository.changeDeactivate(username);
    }

    //Änderung des Status auf aktiv
    public void changeToActive (String username) {
        userRepository.changeActive(username);
    }

}