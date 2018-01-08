package de.hsba.two.organizer.event;

import de.hsba.two.organizer.user.User;
import de.hsba.two.organizer.user.UserAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EventServiceIntegrationTest {

    @Autowired
    private EventService eventService;

    @PersistenceContext
    private EntityManager entityManager;

    private User testUser = new User("12345");
    private User testUser2 = new User("54321");
    private User testUser3 = new User("15243");

    @Before
    public void setUp() throws Exception {
        entityManager.persist(testUser);
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(new UserAdapter(testUser), null));
    }

    @Test
    public void testGetOwnerEvents() throws Exception {

        //gegeben
        Event ev1 = buildEvent();

        //wenn
        Collection<Event> events = eventService.getEventsByOwner();

        //dann
        assertEquals(1, events.size());
        assertTrue(events.contains(ev1));
    }

    @Test
    public void testGetParticipantsSize() throws Exception {
        //gegeben
        List<User> list = Arrays.asList(testUser2, testUser3);
        Event ev2 = buildEventTime(list);

        //wenn
        List<EventTime> evTime = ev2.getTimes();
        EventTime time = evTime.get(0);

        //dann
        assertEquals(2, time.getParticipants().size());
        assertTrue(time.getParticipants().containsAll(list));

    }

    private Event buildEvent() {
        Event event = new Event();
        event.setName("Test");
        event.setCategory("TestKategorie");
        return eventService.createEvent(event);
    }

    private Event buildEventTime(List<User> list) {
        Event event = new Event();
        event.setName("Test");
        event.setCategory("TestKategorie");
        EventTime evTime1 = new EventTime();
        evTime1.setTitle("Test");
        evTime1.setDescription("Test");
        evTime1.setDate("01-01-2018");
        evTime1.setTime("01:00");
        evTime1.setMaxParticipants(2);
        evTime1.setDuration("01:00");
        evTime1.setParticipants(list);

        eventService.addEventTime(event, evTime1);

        return eventService.createEvent(event);

    }


}
