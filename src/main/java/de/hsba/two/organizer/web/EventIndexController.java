package de.hsba.two.organizer.web;

import de.hsba.two.organizer.event.Event;
import de.hsba.two.organizer.event.EventService;
import de.hsba.two.organizer.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/events")
public class EventIndexController {

    private final EventService eventService;
    private final UserService userService;

    public EventIndexController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
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

    @RequestMapping(path="/loggedIn")
    public String checkStatus() {
        String currentUserName = userService.getUserName();
        String status = userService.getStatus(currentUserName);

        if (status.equals("true"))
            return "redirect:/events";
        else
            return "redirect:/logout";
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
