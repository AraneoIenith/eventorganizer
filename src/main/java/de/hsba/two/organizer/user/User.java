package de.hsba.two.organizer.user;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;

@Entity
public class User {

    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserAdapter) {
            return ((UserAdapter) principal).getUser();
        }
        return null;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private boolean active;

    @Column(unique = true)
    @Basic(optional = false)
    private String username;

    @Basic(optional = false)
    private String firstname;

    @Basic(optional = false)
    private String password;

    @Basic(optional = false)
    private String role;

    private User() {
    }

    public User(String username, String firstname, String password, String role, boolean active) {
        this.username = username;
        this.firstname = firstname;
        this.password = password;
        this.role = role;
        this.active = true;
    }

    public Long getId() { return id; }

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

    public void setActive(boolean active) { this.active = true;}

    //Getter bei Boolean ist immer mit is+Attributnamen zu definieren
    public Boolean isActive() {return this.active;}
}
