package tnf.back.mvc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tnf.back.db.entityes.Favorites;
import tnf.back.db.entityes.Route;
import tnf.back.db.entityes.User;
import tnf.back.db.repo.FavoritesRepository;
import tnf.back.db.repo.RouteRepository;
import tnf.back.db.repo.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class AccountController {

    private final UserRepository repository;
    private final RouteRepository routeRepository;
    private final FavoritesRepository favoritesRepository;

    public AccountController(
            UserRepository repository,
            RouteRepository routeRepository,
            FavoritesRepository favoritesRepository
    ) {
        this.repository = repository;
        this.routeRepository = routeRepository;
        this.favoritesRepository = favoritesRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/fav/{id}")
    public String addFavorite(
            @AuthenticationPrincipal User user,
            @PathVariable("id") Long id
    ){
        var route = routeRepository.findById(id).get();

        Favorites fav = isFavorite(user, route);
        if (fav == null)
            favoritesRepository.saveAndFlush(new Favorites(user, route));
        else
            favoritesRepository.delete(fav);

        return "redirect:/routes/" + id;
    }

    private Favorites isFavorite(User user, Route route){
        if (user != null && route != null){
            var favorites = favoritesRepository.findByUser(user);
            for (var fav : favorites)
                if (fav.getRoute().getId() == route.getId())
                    return fav;
        }
        return null;
    }

    @GetMapping("/account")
    public String open(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("uploadPath", uploadPath);

        var createdRoutes = routeRepository.findByAuthor(user);
        if (createdRoutes != null && createdRoutes.size() > 0)
            model.addAttribute("routes", routeRepository.findByAuthor(user));

        ArrayList<Route> favRoutes = new ArrayList<>();
        for (var e : favoritesRepository.findByUser(user))
            favRoutes.add(e.getRoute());
        model.addAttribute("favorites", favRoutes.size() > 0 ? favRoutes : null);
        return "account";
    }

    @PostMapping(value = "/account/changeImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String changeImage(
            @RequestParam("imageFile") MultipartFile imageFile,
            @AuthenticationPrincipal User user,
            Model model
    ) {
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
            @AuthenticationPrincipal User user,
            Model model
    ) {
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
    ) {
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