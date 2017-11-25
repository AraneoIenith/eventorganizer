package de.hsba.two.organizer.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String persno;

    private String password;

    private String role;

    private User() {
    }

    public User(String username, String persno, String password, String role) {
        this.username = username;
        this.persno = persno;
        this.password = password;
        this.role = role;
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

    public String getPersno() {
        return persno;
    }

    public void setPersno(String persno) {
        this.persno = persno;
    }
}
