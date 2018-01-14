package de.hsba.two.organizer.web;

import de.hsba.two.organizer.event.EventService;
import de.hsba.two.organizer.user.User;
import de.hsba.two.organizer.user.UserService;
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

    //Umleitung auf die Profilseite inklusive Prüfung, ob der User auf seine eigene Seite zugreifen will
    //Übergabe des Users, der Events des Nutzers und der Termine, für die der Nutzer angemeldet ist
    @GetMapping(path = "/{username}")
    public String show(@PathVariable("username") String username, Model model) {
        User currentUserObj = userService.getUserObj();
        String currentUserName = userService.getUserName();
        model.addAttribute("user", currentUserObj);
        model.addAttribute("events", eventService.getEventsByOwner());
        model.addAttribute("UserEventTimes", eventService.getUserEventTimes());
        if (!username.equals(currentUserName)) {
            return "redirect:/accessDenied/";
        } else {
            return "profile/show";
        }
    }

    //Passwort ändern inklusive Prüfung, ob das alte Passwort korrekt ist, das neue nicht leer ist und mit dem wiederholten übereinstimmt
    @PostMapping(path = "/{username}")
    public String update(@PathVariable("username") String username, String passwordold, String passwordnew, String passwordnew2, Model model) {
        String password = userService.getPassword(username);
        boolean passmatching = userService.EncPass(passwordold, password);
        if (passmatching == false) {
            return "redirect:/profile/" + username + "?passold";
        } else if (passwordnew == "") {
            return "redirect:/profile/" + username + "?passempty";
        } else if (!passwordnew.equals(passwordnew2)) {
            return "redirect:/profile/" + username + "?passnew";
        } else {
            userService.changePassword(username, passwordnew);
            return "redirect:/profile/" + username + "?succ";
        }
    }

}
