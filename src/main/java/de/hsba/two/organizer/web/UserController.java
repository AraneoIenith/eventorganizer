package de.hsba.two.organizer.web;
import de.hsba.two.organizer.user.User;
import de.hsba.two.organizer.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
    @GetMapping(path = "/edit/{username}")
    public String edit(@PathVariable("username") String username, Model model) {
        model.addAttribute("user", userService.getUser(username));
        return "users/edit";
    }

    @PostMapping(path = "/edit/password/{username}")
    public String editpass(@PathVariable("username") String username, String passwordnew, String passwordnew2, Model model)
    {
        if (passwordnew == "") {
            return "redirect:/users/edit/" + username + "?passempty";
        }
        else if (!passwordnew.equals(passwordnew2)) {
            return "redirect:/users/edit/" + username + "?passnew";
        }
        else {
            model.addAttribute("user", userService.getUser(username));
            userService.changePassword(username, passwordnew);
            return "redirect:/users/edit/" + username + "?passaccepted";
        }
    }

    @PostMapping(path = "/edit/username/{username}")
    public String editusername(@PathVariable("username") String username, String usernamenew, Model model)
    {
        boolean usermatching = userService.matchUser(usernamenew);
        if (!usermatching) {
            model.addAttribute("user", userService.getUser(username));
            userService.changeUsername(username, usernamenew);
            return "redirect:/users/edit/" + usernamenew + "?usernameaccepted";
        }
        else {
            return "redirect:/users/edit/" + username + "?userexist";
        }
    }

    @PostMapping(path = "/edit/firstname/{username}")
    public String editfirstname(@PathVariable("username") String username, String firstnamenew, Model model) {
        model.addAttribute("user", userService.getUser(username));
        userService.changefirstname(username, firstnamenew);
            return "redirect:/users/edit/" + username + "?firstnameaccepted";
    }
    @PostMapping(path = "/edit/role/{username}")
    public String editrole(@PathVariable("username") String username, String rolenew, Model model)
    {   model.addAttribute("user", userService.getUser(username));
        userService.changeRole(username, rolenew);
        return "redirect:/users/edit/" + username + "?roleaccepted";
    }

    @PostMapping(path = "/create")
    public String create(@Valid User user, String username,  String firstname, String password, String password2, String role){

        boolean usermatching = userService.matchUser(username);

        if (usermatching)
            return "redirect:/users/" + "?userexist";

        if (!password.equals(password2)) {
            return "redirect:/users/" + "?passnew";
        }
        else {
            user = userService.createUser(username, firstname, password, role, true);
            return "redirect:/users/" + "?usercreated";
        }



    }

    @PostMapping(path = "/deactivate/{username}")
        public String deactivate(@PathVariable("username") String username){
        userService.changeToDeactive(username);

        return "redirect:/users/";
        }

    @PostMapping(path = "/activate/{username}")
    public String activate(@PathVariable("username") String username){
        userService.changeToActive(username);

        return "redirect:/users/";
    }
}
