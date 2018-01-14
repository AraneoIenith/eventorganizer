package de.hsba.two.organizer.web;

import de.hsba.two.organizer.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    //Anzeige Bearbeitungsdetailseite eines Nutzers
    @GetMapping(path = "/edit/{username}")
    public String edit(@PathVariable("username") String username, Model model) {
        model.addAttribute("user", userService.getUser(username));
        return "users/edit";
    }

    //Änderung des Passwortes eines Users.
    //Validierungen: prüfen, ob das Feld leer ist und ob die eingegebenen Passwörter übereinstimmen. Falls es nicht der Fall ist, werden
    //auf die in HTML eingestellten Fehlermeldungen verwiesen
    //Wenn alles korrekt ist, wird im Service die entsprechende Methode zur Änderung des Passwortes aufgerufen und Erfolgsmeldung erscheint
    @PostMapping(path = "/edit/password/{username}")
    public String editpass(@PathVariable("username") String username, String passwordnew, String passwordnew2, Model model) {
        if (passwordnew == "") {
            return "redirect:/users/edit/" + username + "?passempty";
        } else if (!passwordnew.equals(passwordnew2)) {
            return "redirect:/users/edit/" + username + "?passnew";
        } else {
            model.addAttribute("user", userService.getUser(username));
            userService.changePassword(username, passwordnew);
            return "redirect:/users/edit/" + username + "?passaccepted";
        }
    }

    //Änderung der Personalnummer eines Users.
    //Validierung: prüfen, ob die eingegebene Personalnummer bereits existiert (Personalnr. soll einzigartig sein)
    //Wenn Personalnr. noch nicht vergeben ist, wird diese als Parameter an die entsprechende Methode an den Service übergeben; Erfolgsmeldung erscheint
    @PostMapping(path = "/edit/username/{username}")
    public String editusername(@PathVariable("username") String username, String usernamenew, Model model) {
        boolean usermatching = userService.matchUser(usernamenew);
        if (!usermatching) {
            model.addAttribute("user", userService.getUser(username));
            userService.changeUsername(username, usernamenew);
            return "redirect:/users/edit/" + usernamenew + "?usernameaccepted";
        } else {
            return "redirect:/users/edit/" + username + "?userexist";
        }
    }

    //Änderung des Vornames eines Users.
    //Der richtige User wird anhand des mitgegebenen Parameters (die Personalnr. rausgesucht). An dem User wird der alte Vorname mit dem neuen Vornamen ersetzt.
    @PostMapping(path = "/edit/firstname/{username}")
    public String editfirstname(@PathVariable("username") String username, String firstnamenew, Model model) {
        model.addAttribute("user", userService.getUser(username));
        userService.changefirstname(username, firstnamenew);
        return "redirect:/users/edit/" + username + "?firstnameaccepted";
    }

    //Änderung der Rolle.
    //ALte Rolle wird mit der neu eingegebenen Rolle (rolenew) ersetzt.
    @PostMapping(path = "/edit/role/{username}")
    public String editrole(@PathVariable("username") String username, String rolenew, Model model) {
        model.addAttribute("user", userService.getUser(username));
        userService.changeRole(username, rolenew);
        return "redirect:/users/edit/" + username + "?roleaccepted";
    }


    //Anlegen eines neuen Users
    //Prüfung, ob Passwörter übereinstimmen, dann erst Aufruf des UserServices
    //Grund: Initialnutzer sollen ohne Passwortprüfung angelegt werden
    @PostMapping(path = "/create")
    public String create(String username, String firstname, String password, String password2, String role) {
        if (!password.equals(password2)) {
            return "redirect:/users/" + "?passnew";
        } else {
            String param = userService.createUser(username, firstname, password, role, true);
            return "redirect:/users/" + "?" + param;
        }
    }

    //Änderung des Status auf inaktiv.
    //Personalnummer wird als Parameter für die Methode changeToDeactive im Service mitgegeben. Verlinkt wird anschließend auf die Nutzerverwaltung.
    @PostMapping(path = "/deactivate/{username}")
    public String deactivate(@PathVariable("username") String username) {
        userService.changeToDeactive(username);

        return "redirect:/users/";
    }

    //Änderung des Status auf aktiv.
    //Personalnummer wird als Parameter für die Methode changeToDeactive im Service mitgegeben. Verlinkt wird anschließend auf die Nutzerverwaltung.
    @PostMapping(path = "/activate/{username}")
    public String activate(@PathVariable("username") String username) {
        userService.changeToActive(username);

        return "redirect:/users/";
    }
}
