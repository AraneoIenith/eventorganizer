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
    public Integer getParticipantsSize(@PathVariable("id") Long id){
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

    @PostMapping(path = "/{id}/edit/title")
    public String editEventtimeTitle(@PathVariable("id") Long id, String titlenew, Model model) {
        model.addAttribute("eventtime", eventService.findTime(id));
        eventService.changeEventtimeTitle(id, titlenew);
        return "redirect:/eventtime/" + id  + "/?titleaccepted";
    }

    @PostMapping(path = "/{id}/edit/description")
    public String editEventtimeDescription(@PathVariable("id") Long id, String descriptionnew, Model model) {
        model.addAttribute("eventtime", eventService.findTime(id));
        eventService.changeEventtimeDescription(id, descriptionnew);
        return "redirect:/eventtime/" + id + "/?descriptionaccepted";
    }

    @PostMapping(path = "/{id}/edit/date")
    public String editEventtimeDate(@PathVariable("id") Long id, String datenew, Model model) {
        model.addAttribute("eventtime", eventService.findTime(id));
        eventService.changeEventtimeDate(id, datenew);
        return "redirect:/eventtime/" + id + "/?dateaccepted";
    }

    @PostMapping(path = "/{id}/edit/time")
    public String editEventtimeTime(@PathVariable("id") Long id, String timenew, Model model) {
        model.addAttribute("eventtime", eventService.findTime(id));
        eventService.changeEventtimeTime(id, timenew);
        return "redirect:/eventtime/" + id + "/?timeaccepted";
    }

    @PostMapping(path = "/{id}/edit/duration")
    public String editEventtimeDuration(@PathVariable("id") Long id, String durationnew, Model model) {
        model.addAttribute("eventtime", eventService.findTime(id));
        eventService.changeEventtimeDuration(id, durationnew);
        return "redirect:/eventtime/" + id + "/?durationaccepted";
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
}