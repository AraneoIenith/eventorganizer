package de.hsba.two.organizer.web;

import de.hsba.two.organizer.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/index";
    }
}
