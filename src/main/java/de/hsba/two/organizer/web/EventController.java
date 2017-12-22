package de.hsba.two.organizer.web;

import de.hsba.two.organizer.event.Event;
import de.hsba.two.organizer.event.EventService;
import de.hsba.two.organizer.event.EventTime;
import de.hsba.two.organizer.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.acl.Owner;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }


    @GetMapping
    public String index(Model model) {
        model.addAttribute("events", eventService.getAll());
        model.addAttribute("event", new Event());
        return "events/index";
    }

    @PostMapping
    public String create(@ModelAttribute("event") @Valid Event event, BindingResult binding){
        if (binding.hasErrors()){
            return "events/index";
        }
        event = eventService.createEvent(event);
        return "redirect:/events/" + event.getId();
    }

    @GetMapping(path = "/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        model.addAttribute("event", eventService.getEvent(id));
        Event event = eventService.getEvent(id);
        if (event == null) {
            throw new NotFoundException();
        } else {
            return "events/show";
        }
    }

    @PostMapping(path = "/{id}")
    public String addTime(@PathVariable("id") Long id, EventTime time) {
        Event event = eventService.getEvent(id);
        eventService.addEventTime(event, time);
        return "redirect:/events/" + event.getId();
    }

    @PostMapping(path = "/{id}/delete")
    public String delete(@PathVariable("id") Long id){
        eventService.delete(id);
        return "redirect:/events/";
    }


}
