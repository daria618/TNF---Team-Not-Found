package tnf.back.mvc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tnf.back.db.entityes.User;
import tnf.back.db.repo.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public String open(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("uploadPath", uploadPath);
        return "account";
    }

    @PostMapping(value = "/account/changeImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String changeImage(
            @RequestParam("imageFile") MultipartFile imageFile,
            @AuthenticationPrincipal User user,
            Model model
    ){
        String imgMsg;
        try {
            String path = UUID.randomUUID() + imageFile.getOriginalFilename();
            Files.write(Paths.get(uploadPath + "/" + path), imageFile.getBytes());
            user.setPhoto(path);
            repository.saveAndFlush(user);
            imgMsg = "Изображение изменено";
        } catch (IOException e) {
            imgMsg = "Ошибка загрузки изображения";
        }

        model.addAttribute("uploadPath", uploadPath);
        model.addAttribute("imgMsg", imgMsg);
        model.addAttribute("user", user);
        return "account";
    }

    @PostMapping(value = "/account/changeData")
    public String changeData(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @AuthenticationPrincipal User user,
            Model model
    ){
        String dataMsg;
        if (user.getUsername().equals(user)) dataMsg = "указано текущее имя";
        else if (user.getEmail().equals(email)) dataMsg = "указана текущяя почта";
        // TODO: 31.05.2023 Изменение почты и имени

        model.addAttribute("user", user);
        model.addAttribute("uploadPath", uploadPath);
        return "account";
    }

    @PostMapping(value = "/account/changePwd")
    public String changePwd(
            @RequestParam("old") String pwdOld,
            @RequestParam("new") String pwdNew,
            @RequestParam("confirm") String pwdConfirm,
            @AuthenticationPrincipal User user,
            Model model
    ){
        String pwdMsg;

        if (!user.getPassword().equals(pwdOld)) pwdMsg = "Указан неверный пароль!";
        else if (!pwdNew.equals(pwdConfirm)) pwdMsg = "Пароли не совпадают!";
        else if (user.getPassword().equals(pwdNew)) pwdMsg = "Указан действующий пароль";
        else {
            user.setPassword(pwdNew);
            repository.saveAndFlush(user);
            pwdMsg = "Пароль был изменен";
        }

        model.addAttribute("user", user);
        model.addAttribute("pwdMsg", pwdMsg);
        model.addAttribute("uploadPath", uploadPath);
        return "account";
    }
}