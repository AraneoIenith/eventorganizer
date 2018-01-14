package de.hsba.two.organizer.user;

import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private boolean active;
    @Column(unique = true)
    private String username;
    private String firstname;
    private String password;
    private String role;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String firstname, String password, String role, boolean active) {
        this.username = username;
        this.firstname = firstname;
        this.password = password;
        this.role = role;
        this.active = true;
    }

    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserAdapter) {
            return ((UserAdapter) principal).getUser();
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setActive(boolean active) {
        this.active = true;
    }

    //Getter bei Boolean ist immer mit is+Attributnamen zu definieren
    public Boolean isActive() {
        return this.active;
    }
}
