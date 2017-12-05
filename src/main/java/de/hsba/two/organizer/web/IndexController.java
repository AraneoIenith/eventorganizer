package de.hsba.two.organizer.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping
    public String index() {
        return "redirect:/events/";
    }

    @RequestMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof AnonymousAuthenticationToken ? "login" : "redirect:/";
        //auf den SecurityContext zugreifen, um zu verstehen, ob der User angemeldet ist oder nicht. Angemeldete
        //User sollen nicht auf index.html zugreifen können
    }

    @RequestMapping("/accessDenied")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDenied() {
        return "errors/403";
    }

    @RequestMapping("/**/*")
    public void catchAll() {
        throw new NotFoundException();
    }

}
