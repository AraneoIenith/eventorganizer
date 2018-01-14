package de.hsba.two.organizer.web;

import de.hsba.two.organizer.event.Event;
import de.hsba.two.organizer.event.EventService;
import de.hsba.two.organizer.event.EventTime;
import de.hsba.two.organizer.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/events/{id}")
public class EventShowController {

    private final EventService eventService;
    private final UserService userService;

    public EventShowController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @ModelAttribute("event")
    public Event getEvent(@PathVariable("id") Long id) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            throw new NotFoundException();
        }
        return event;
    }

    @GetMapping
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("eventTime", new EventTime());
        Event event = eventService.getEvent(id);
        if (event == null) {
            throw new NotFoundException();
        } else {
            return "events/show";
        }
    }

    //Änderung des Namens der Veranstaltung mit Übergabe der id der Veranstaltung und des neuen Namens(namenew)
    @PostMapping(path = "/edit/name")
    public String editEventName(@PathVariable("id") Long id, String namenew) {
        eventService.changeEventName(id, namenew);
        return "redirect:/events/" + id + "?nameaccepted";
    }

    //Änderung der Kategorie mit Übergabe der id der Veranstaltung und der neuen Kategorie(categorynew)
    @PostMapping(path = "/edit/category")
    public String editEventCategory(@PathVariable("id") Long id, String categorynew) {
        eventService.changeEventCategory(id, categorynew);
        return "redirect:/events/" + id + "?categoryaccepted";
    }

    @PostMapping(path = "/eventTime")
    public String addTime(Model model, @PathVariable("id") Long id, @ModelAttribute("eventTime") @Valid EventTime time, BindingResult binding) {
        Event event = eventService.getEvent(id);
        if (binding.hasErrors()) {
            return "events/show" ;
        }
        eventService.addEventTime(event, time);
        return "redirect:/events/" + event.getId();
    }

    @PostMapping(path = "/delete")
    public String delete(@PathVariable("id") Long id) {
        eventService.delete(id);
        return "redirect:/events/";
    }


}
