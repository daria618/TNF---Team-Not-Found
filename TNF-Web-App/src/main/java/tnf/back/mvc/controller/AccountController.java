package tnf.back.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tnf.back.db.entityes.User;
import tnf.back.db.repo.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AccountController {

    private final UserRepository repository;

    public AccountController(UserRepository repository) {
        this.repository = repository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/account")
    public String account(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "/account";
    }

    @PostMapping("/account/edit")
    public String acceptForm(
            @AuthenticationPrincipal User user,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("file") MultipartFile file,
            Model model
    ) {

        if (!file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                if (!uploadDir.mkdirs())
                    throw new RuntimeException("Can't create upload dir");

            String uuidName = UUID.randomUUID() + "." + file.getOriginalFilename();

            try {
                file.transferTo(new File(uploadDir + uuidName));
            } catch (IOException e) {
                throw new RuntimeException("Can't transfer file" + e);
            }

            user.setPhoto(uuidName);
        }

        if (!user.getEmail().equals(email))
            user.setEmail(email);
        if (!user.getUsername().equals(name))
            user.setUsername(name);

        repository.save(user);

        model.addAttribute("user", user);
        return "account";
    }

}