package de.hsba.two.organizer.event;

import de.hsba.two.organizer.user.User;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Bitte geben Sie einen Namen ein")
    @Basic(optional = false)
    private String name;

    @NotBlank(message = "Bitte geben Sie eine Kategorie ein")
    @Basic(optional = false)
    private String category;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventTime> times;

    @ManyToOne(optional = false)
    private User owner;

    @PrePersist
    private void onPersist() {
        this.owner = User.getCurrentUser();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EventTime> getTimes() {
        if (times == null) {
            times = new ArrayList<>();
        }
        return times;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
