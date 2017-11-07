package de.hsba.two.organizer.event;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class Event {
    @Id
    @GeneratedValue
    private Long id;
}
