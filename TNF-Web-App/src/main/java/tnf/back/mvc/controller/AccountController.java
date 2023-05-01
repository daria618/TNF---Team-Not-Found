package tnf.back.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tnf.back.db.entityes.User;
import tnf.back.db.repo.UserRepository;

@Controller
public class AccountController {

    @Autowired
    UserRepository repository;

    @GetMapping("/account")
    public String account(@AuthenticationPrincipal User user, Model model){
        return "/account";
    }
}