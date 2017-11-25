package de.hsba.two.organizer.user;

import org.omg.CORBA.StringHolder;
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
        createUser("Anna","00000", "00000", "HR");
        createUser("Berta","11111", "11111", "ORGANIZER");
    }

    private void createUser(String username, String persno, String password, String role) {
        userRepository.save(new User(username, persno, passwordEncoder.encode(password), role));
    }

    public User getUser(String username) {
       return userRepository.findByName(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
