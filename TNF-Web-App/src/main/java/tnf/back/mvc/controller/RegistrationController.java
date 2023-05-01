package tnf.back.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tnf.back.db.entityes.Role;
import tnf.back.db.entityes.User;
import tnf.back.db.repo.UserRepository;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository repository;

    @GetMapping("/registration")
    public String reg() {
        return "/registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirm_password") String confirm_password,
            Model model
    ) {
        User u = repository.findByUsername(username);
        if (u != null) {
            return "/registration";
        }

        User user = new User(username, password, email, true, Collections.singleton(Role.USER));
        repository.save(user);

        return "redirect:/login";
    }

}