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
            model.addAttribute("error", "Пользователь с таким имененм уже существует");
            return "/registration";
        }
        if (!password.equals(confirm_password)){
            model.addAttribute("error", "Пароли не совпадают");
            return "/registration";
        }

        User user = new User(username, password, email, true, Collections.singleton(Role.USER));
        repository.save(user);

        return "redirect:/login";
    }

}