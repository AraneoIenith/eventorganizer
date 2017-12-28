package de.hsba.two.organizer.event;

import de.hsba.two.organizer.user.User;
import de.hsba.two.organizer.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class EventService {

    private final EventRepository repository;
    private final EventTimeRepository timeRepository;
    private final UserService userService;


    public EventService(EventRepository repository, EventTimeRepository timeRepository, UserService userService) {
        this.repository = repository;
        this.timeRepository = timeRepository;
        this.userService = userService;
    }

    public Collection<Event> getAll() {
        return repository.findAll();
    }

    public Collection<Event> getEventsByOwner() {
        User currentuser = userService.getUserObj();
        return repository.findByOwner(currentuser);
    }

    //Liste mit Terminen, zu denen sich der User angemeldet hat
    public List<EventTime> getUserEventTimes() {
        User currentuser = userService.getUserObj();
        return timeRepository.findUserEventTimes(currentuser);
    }

    public Event createEvent(Event event) {
        return repository.save(event); // save one entity
    }

    public Integer getParticipantsSize(Long id) {
        EventTime eventTime = findTime(id);
        return eventTime.getParticipants().size();
    }

    public void signUp(EventTime eventTime) {
        User currentuserobj = userService.getUserObj();
        List<User> currentParticipants = eventTime.getParticipants();
        currentParticipants.add(currentuserobj);
    }

    public void signOut(EventTime eventTime) {
        User currentuserobj = userService.getUserObj();
        List<User> currentParticipants = eventTime.getParticipants();
        currentParticipants.remove(currentuserobj);
    }

    /*@PostConstruct
    public void init() {
        Event event = new Event();
        event.setName("Haspa Selbsverteidigung");
        event.setCategory("Kampf");
        createEvent(event, "11111");
    }*/

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

    public void deleteTime(Long id) {
        timeRepository.delete(id);
    }

}
