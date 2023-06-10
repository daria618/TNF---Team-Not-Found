package tnf.back.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tnf.back.db.repo.RouteRepository;

@Controller
public class RoutesController {

    private final RouteRepository repository;

    public RoutesController(RouteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/routes")
    public String openRoutes(Model model){
        var routes = repository.findAll();
        model.addAttribute("routes", routes);
        model.addAttribute("test", "TEST");
        return "routes";
    }

}