package tnf.back.mvc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tnf.back.db.repo.RouteRepository;

@Controller
public class EditRouteController {

    @Value("${upload.path}")
    private String uploadPath;
    private final RouteRepository repository;

    public EditRouteController(RouteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/edit/{route_id}")
    public String openEditor(
            @PathVariable("route_id") Long id,
            Model model
    ) {
        var route = repository.findById(id).get();
        model.addAttribute("isLoaded", true);
        model.addAttribute("route", route);
        return "create_route";
    }
}