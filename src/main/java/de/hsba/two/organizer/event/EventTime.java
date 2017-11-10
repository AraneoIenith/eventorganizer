package de.hsba.two.organizer.event;

import de.hsba.two.organizer.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EventTime {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Event event;

    private String title;

    private Long maxParticipant;

    private String description;

    private String date;

    @ManyToMany
    private List<User> participant;


    public EventTime(){}

    public EventTime(String title, Long maxParticipant, String description){
        this.title = title;
        this.maxParticipant = maxParticipant;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getMaxParticipant() {
        return maxParticipant;
    }

    public void setMaxParticipant(Long maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getParticipant() {
        if (participant == null){
            participant = new ArrayList<>();
        }
        return participant;
    }

    public void setParticipant(List<User> participant) {
        this.participant = participant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
