package de.hsba.two.organizer.web;

import de.hsba.two.organizer.event.EventService;
import de.hsba.two.organizer.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final EventService eventService;
    private final UserService userService;

    public ProfileController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping(path = "/{username}")
    public String show(@PathVariable("username") String username, Model model) {
        String currentuser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userService.getUser(currentuser));
        model.addAttribute("events",eventService.getByOwner(currentuser));
        if (!username.equals(currentuser)) {
            return "redirect:/accessDenied/";
        }
        else {
            return "profile/show";
        }
    }

    @PostMapping(path = "/{username}")
        public String update(@PathVariable("username") String username, String passwordold, String passwordnew, String passwordnew2, Model model)
    {
        String password = userService.getPassword(username);
        boolean passmatching = userService.EncPass(passwordold, password);
        if (passmatching == false) {
            return "redirect:/profile/" + username + "?passold";
        }
        else if (passwordnew == "") {
            return "redirect:/profile/" + username + "?passempty";
        }
        else if (!passwordnew.equals(passwordnew2)) {
            return "redirect:/profile/" + username + "?passnew";
        }
        else {
            String currentuser = SecurityContextHolder.getContext().getAuthentication().getName();
            model.addAttribute("user", userService.getUser(currentuser));
            model.addAttribute("events",eventService.getByOwner(currentuser));
            userService.changePassword(username, passwordnew);
            return "redirect:/profile/" + username + "?succ";
        }
    }

}
