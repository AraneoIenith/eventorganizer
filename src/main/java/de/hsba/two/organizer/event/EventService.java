package de.hsba.two.organizer.event;

import de.hsba.two.organizer.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class EventService {

    private final EventRepository repository;
    private final EventTimeRepository timeRepository;


    public EventService(EventRepository repository, EventTimeRepository timeRepository) {
        this.repository = repository;
        this.timeRepository = timeRepository;
    }

    public Collection<Event> getAll() {
        return repository.findAll();
    }

    public Event createEvent(String name, String category) {
        Event event = new Event();
        event.setName(name);
        event.setCategory(category);
        return repository.save(event); // save one entity
    }

    public User getCurrentUserObj(){
        String currentuser = SecurityContextHolder.getContext().getAuthentication().getName();
        return timeRepository.findCurrentUser(currentuser);
    }

    public boolean isSignedUp(EventTime eventTime) {

        User currentuserobj = getCurrentUserObj();
        List<User> currentParticipants = eventTime.getParticipants();

        if (currentParticipants.contains(currentuserobj)) {
            return true;
        } else {
            return false;
        }
    }

    public void signUp(EventTime eventTime) {
        User currentuserobj = getCurrentUserObj();
        List<User> currentParticipants = eventTime.getParticipants();
        currentParticipants.add(currentuserobj);

    }

    @PostConstruct
    public void init() {
        createEvent("Haspa Selbstverteidigung", "Kampf");
        createEvent("Selbstbemitleidung", "Erbärmlich");
    }

    public Event getEvent(Long id) {
        return repository.findOne(id);
    }

    public void addEventTime(Event event, EventTime time) {
        time.setEvent(event);
        event.getTimes().add(time);
    }

    public EventTime findTime(Long id) {
        return timeRepository.findOne(id);
    }

    public void delete(Long id) {
        repository.delete(id);
    }


}
