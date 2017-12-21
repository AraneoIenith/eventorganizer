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

    public Collection<Event> getByOwner(String currentuser) {return repository.findByOwner(currentuser);}

    public Event createEvent(String name, String category, String owner) {
        Event event = new Event();
        event.setName(name);
        event.setCategory(category);
        event.setOwner(owner);
        return repository.save(event); // save one entity
    }

    //Den aktuellen User (-namen) aus dem Security Context holen
    public User getCurrentUserObj() {
        String currentuser = SecurityContextHolder.getContext().getAuthentication().getName();
        return timeRepository.findCurrentUser(currentuser);
    }

    public Integer getParticipantsSize(Long id) {
        EventTime eventTime = findTime(id);
        return eventTime.getParticipants().size();
    }

    public void signUp(EventTime eventTime) {
        User currentuserobj = getCurrentUserObj();
        List<User> currentParticipants = eventTime.getParticipants();
        currentParticipants.add(currentuserobj);
    }

    public void signOut(EventTime eventTime) {
        User currentuserobj = getCurrentUserObj();
        List<User> currentParticipants = eventTime.getParticipants();
        currentParticipants.remove(currentuserobj);
    }

    @PostConstruct
    public void init() {
        createEvent("Haspa Selbstverteidigung", "Kampf", "11111");
        createEvent("Selbstbemitleidung", "Erbärmlich", "33333");
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

    public void deleteTime(Long id){timeRepository.delete(id);}

}
