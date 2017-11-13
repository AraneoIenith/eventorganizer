package de.hsba.two.organizer.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {this.repository = repository;}

    public Collection<Event> getAll(){return repository.findAll();}

    public Event createEvent(String name, String category){
        Event event = new Event();
        event.setName(name);
        event.setCategory(category);
        return repository.save(event); // save one entity
    }

    public Event getEvent(Long id) {
        return repository.findOne(id);
    }

    public void addEventTime(Event event, EventTime time) {
        time.setEvent(event);
        event.getTimes().add(time);
    }

    public void delete(Long id){
        repository.delete(id);
    }



}
