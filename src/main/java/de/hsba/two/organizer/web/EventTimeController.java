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

    //Anzeige der Bearbeitungsseite eines Termins
    @GetMapping(path = "/{id}/edit")
    public String editEventtime(@PathVariable("id") Long id, Model model) {
        model.addAttribute("eventtime", eventService.findTime(id));
        EventTime eventtime = eventService.findTime(id);
        if (eventtime == null) {
            throw new NotFoundException();
        } else {
            return "events/editeventtime";
        }
    }

    //Änderung des Titels eines Termins
    //Übergabe der id des Termins (für Identifikation des Termins) und neuer Titel(titelnew)
    @PostMapping(path = "/{id}/edit/title")
    public String editEventtimeTitle(@PathVariable("id") Long id, String titlenew, Model model) {
        model.addAttribute("eventtime", eventService.findTime(id));
        eventService.changeEventtimeTitle(id, titlenew);
        return "redirect:/eventtime/" + id + "/edit/" + "?titleaccepted";
    }

    //Änderung der Beschreibung eines Termins
    //Übergabe der id des Termins (für Identifikation des Termins) und neuer Beschreibung(descriptionnew)
    @PostMapping(path = "/{id}/edit/description")
    public String editEventtimeDescription(@PathVariable("id") Long id, String descriptionnew, Model model) {
        model.addAttribute("eventtime", eventService.findTime(id));
        eventService.changeEventtimeDescription(id, descriptionnew);
        return "redirect:/eventtime/" + id + "/edit/" + "?descriptionaccepted";
    }

    //Änderung des Datums eines Termins
    //Übergabe der id des Termins (für Identifikation des Termins) und neues Datum(datenew)
    @PostMapping(path = "/{id}/edit/date")
    public String editEventtimeDate(@PathVariable("id") Long id, String datenew, Model model) {
        model.addAttribute("eventtime", eventService.findTime(id));
        eventService.changeEventtimeDate(id, datenew);
        return "redirect:/eventtime/" + id + "/edit/" + "?dateaccepted";
    }

    //Änderung der Uhrzeit des Termins
    //Übergabe der id des Termins (für Identifikation des Termins) und neue Uhrzeit(timenew)
    @PostMapping(path = "/{id}/edit/time")
    public String editEventtimeTime(@PathVariable("id") Long id, String timenew, Model model) {
        model.addAttribute("eventtime", eventService.findTime(id));
        eventService.changeEventtimeTime(id, timenew);
        return "redirect:/eventtime/" + id + "/edit/" + "?timeaccepted";
    }

    //Änderung der Dauer eines Termins
    //Übergabe der id des Termins (für Identifikation des Termins) und der neuen Dauer(durationnew)
    @PostMapping(path = "/{id}/edit/duration")
    public String editEventtimeDuration(@PathVariable("id") Long id, String durationnew, Model model) {
        model.addAttribute("eventtime", eventService.findTime(id));
        eventService.changeEventtimeDuration(id, durationnew);
        return "redirect:/eventtime/" + id + "/edit/" + "?durationaccepted";
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