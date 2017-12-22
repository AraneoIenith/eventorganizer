package de.hsba.two.organizer.web;

import de.hsba.two.organizer.event.EventService;
import de.hsba.two.organizer.event.EventTime;
import de.hsba.two.organizer.user.User;
import de.hsba.two.organizer.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/eventtime")
public class EventTimeController {

    private final EventService eventService;
    private final UserService userService;

    public EventTimeController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    //Aktuellen Nutzer zum Abgleich mit der Teilnehmerliste vorhanden haben
    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return userService.getUserObj();
    }

    //Größe der Teilnehmerliste zum Abgleich mit der maximalen Zahl an Teilnehmern
    @ModelAttribute("participantsSize")
    public Integer getPasrticipantsSize(@PathVariable("id") Long id){
        return eventService.getParticipantsSize(id);
    }

    @GetMapping(path = "{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("eventtime", eventService.findTime(id));
        EventTime eventtime = eventService.findTime(id);
        if (eventtime == null) {
            throw new NotFoundException();
        } else {
            return "events/eventtime";
        }
    }

    //Anmeldung zu einem Termin
    @PostMapping(path = "/{id}/signup")
    public String signup(@PathVariable("id") Long id) {
        EventTime eventTime = eventService.findTime(id);
        eventService.signUp(eventTime);
        return "redirect:/eventtime/" + id;
    }

    //Abmeldung zu einem Termin
    @PostMapping(path = "/{id}/signout")
    public String signout(@PathVariable("id") Long id) {
        EventTime eventTime = eventService.findTime(id);
        eventService.signOut(eventTime);
        return "redirect:/eventtime/" + id;
    }

    @PostMapping(path = "/{id}/delete")
    public String delete(@PathVariable("id") Long id){
        Long eventId = eventService.findTime(id).getEvent().getId();
        eventService.deleteTime(id);
        return "redirect:/events/" + eventId;
    }



//Falls die Seite auch gleichzeitig zum Bearbeiten dienen soll.
//Damit man gleich zwei ModelAttribute hat (eventtime und formEvent)
   /* @ModelAttribute("eventtime")
    public EventTime getEventTime(@PathVariable("id") Long id) {
        EventTime time = eventService.findTime(id);
        return time;
    }*/

    /*@GetMapping
    public String show(Model model, @PathVariable("id") Long id) {
        model.addAttribute("formEvent", getEventTime(id));
        return "events/eventtime";
    }*/


}