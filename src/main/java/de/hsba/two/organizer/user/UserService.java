package de.hsba.two.organizer.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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
        createUser("00000", "Anna", "00000", "HR");
        createUser("11111", "Berta", "11111", "ORGANIZER");
        createUser("22222", "Conny", "22222", "USER");
        createUser("33333", "Dora", "33333", "ORGANIZER");
    }

    private void createUser(String username, String firstname, String password, String role) {
        userRepository.save(new User(username, firstname, passwordEncoder.encode(password), role));
    }

    //Das User Objekt des aktuellen Nutzers
    public User getUserObj() {
        String currentuser = getUserName();
        return userRepository.findByName(currentuser);
    }

    //Der Username (Personalnummer) des aktuellen Nutzers
    public String getUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
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

    public boolean EncPass(String passwordold, String password) {
        return passwordEncoder.matches(passwordold, password);
    }

}
