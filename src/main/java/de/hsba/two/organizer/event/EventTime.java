package de.hsba.two.organizer.event;

import de.hsba.two.organizer.user.User;

import javax.persistence.*;
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

    private Integer maxParticipants;

    private String description;

    private String date;

    private String time;

    @ManyToMany
    private List<User> participants;



    public EventTime(){}

    public EventTime(String title, Integer maxParticipants, String description, Event event, String date, String time){
        this.title = title;
        this.maxParticipants = maxParticipants;
        this.description = description;
        this.date = date;
        this.time= time;
        this.event = event;
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getParticipants() {
        if (participants == null){
            participants = new ArrayList<>();
        }
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }


}
