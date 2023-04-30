package tnf.back.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tnf.back.db.entityes.Role;
import tnf.back.db.entityes.User;
import tnf.back.db.repo.UserRepository;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository repository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

//    @PostMapping("/registration")
//    public String addUser(
//            User user,
//            Model model
//    ) {
//        User u = repository.findByUsername(user.getUsername());
//        if (u != null) {
//            model.addAttribute("message", "USER EXISTS!!!");
//            return "registration";
//        }
//
//        user.setActive(true);
//        user.setRoles(Collections.singleton(Role.USER));
//        repository.save(user);
//        model.addAttribute("message", "Correct!!!");
//
//        return "redirect:/login";
//    }
    @PostMapping("/registration")
    public String addUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model
    ) {
        User u = repository.findByUsername(username);
        if (u != null) {
            model.addAttribute("message", "USER EXISTS!!!");
            return "registration";
        }

        User user = new User(username, password, email, true, Collections.singleton(Role.USER));
        repository.save(user);

        return "redirect:/login";
    }

}