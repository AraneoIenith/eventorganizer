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

    public User createUser(String username, String firstname, String password, String role, boolean active) {
        return userRepository.save(new User(username, firstname, passwordEncoder.encode(password), role, true));
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

    //Username eines beliebigen Users
    public User getUser(String username) {
        return userRepository.findByName(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void changePassword(String username, String passwordnew) {
        userRepository.changePassword(username, passwordEncoder.encode(passwordnew));
    }

    public String getPassword(String username) {
        return userRepository.findPassword(username);
    }

    public String getStatus(String username) {return userRepository.findStatus(username);}

    public boolean EncPass(String passwordold, String password) {
        return passwordEncoder.matches(passwordold, password);
    }

    public void changeUsername(String username, String usernamenew) {
        userRepository.changeUsername(username, usernamenew);
    }

    public void changefirstname (String username, String firstnamenew) {
        userRepository.changeFirstname(username, firstnamenew);
    }

    public void changeRole (String username, String rolenew) {
        userRepository.changeRole(username, rolenew);
    }

    public boolean matchUser(String usernamenew) {
        Object username = userRepository.findUsername(usernamenew);

        if (username == null)
            return false;
        else return true;
    }

    public void changeToDeactive (String username){
       userRepository.changeDeactivate(username);
    }

    public void changeToActive (String username) {
        userRepository.changeActive(username);
    }

}