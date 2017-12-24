package de.hsba.two.organizer.web;

import de.hsba.two.organizer.event.Event;
import de.hsba.two.organizer.event.EventService;
import de.hsba.two.organizer.event.EventTime;
import de.hsba.two.organizer.user.UserService;
import org.hibernate.validator.constraints.EAN;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.acl.Owner;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventIndexController {

    private final EventService eventService;

    public EventIndexController(EventService eventService) {
        this.eventService = eventService;
    }

    @ModelAttribute("events")
    public Collection<Event> getEvents(){
        return eventService.getAll();
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("event", new Event());
        return "events/index";
    }

    @PostMapping
    public String create(Model model, @ModelAttribute("event") @Valid Event event, BindingResult binding){
        if (binding.hasErrors()){
            return "events/index";
        }
        event = eventService.createEvent(event);
        return "redirect:/events/" + event.getId();
    }




}
