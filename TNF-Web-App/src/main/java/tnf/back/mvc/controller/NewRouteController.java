package tnf.back.mvc.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tnf.back.db.entityes.Route;
import tnf.back.db.entityes.RouteCategory;
import tnf.back.db.entityes.User;
import tnf.back.db.repo.RouteRepository;

import java.util.Collections;

@Controller
public class NewRouteController {

    private final RouteRepository repository;

    public NewRouteController(RouteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/editor/create")
    public String open(){
        return "create_route";
    }

    @PostMapping("/editor/create/fin")
    public String saveRoute(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "short_description") String short_description,
            @RequestParam(name = "description") String description,
            Model model
    ){

        

        return "redirect:/routes";
    }

}