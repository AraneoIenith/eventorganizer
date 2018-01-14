package de.hsba.two.organizer.event;

import de.hsba.two.organizer.user.User;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EventTime {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Event event;

    @NotBlank(message = "Bitte geben Sie einen Titel ein")
    @Basic(optional = false)
    private String title;

    @NotNull(message = "Bitte geben Sie eine maximale Teilnehmerzahl an")
    @Min(1)
    @Basic(optional = false)
    private Integer maxParticipants;

    @NotBlank(message = "Bitte geben Sie eine Beschreibung ein")
    @Basic(optional = false)
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Bitte wählen Sie ein Datum aus")
    @Basic(optional = false)
    private String date;

    @NotBlank(message = "Bitte geben Sie eine Uhrzeit an")
    @Basic(optional = false)
    private String time;

    @NotBlank(message = "Bitte geben Sie eine Dauer an")
    @Basic(optional = false)
    private String duration;

    @ManyToMany
    private List<User> participants;


    public EventTime() {
    }

    public EventTime(String title, Integer maxParticipants, String description, Event event, String date, String time, String duration) {
        this.title = title;
        this.maxParticipants = maxParticipants;
        this.description = description;
        this.date = date;
        this.time = time;
        this.event = event;
        this.duration = duration;
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
        if (participants == null) {
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


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
