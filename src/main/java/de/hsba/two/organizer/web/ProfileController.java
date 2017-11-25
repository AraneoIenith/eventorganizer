package de.hsba.two.organizer.web;

import de.hsba.two.organizer.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    private HttpServletRequest currentuser;

    @GetMapping(path = "/{username}")
    public String show(@PathVariable("username") String username, Model model) {
        model.addAttribute("user", userService.getUser(username));
        return "profile/show";

    }
}
