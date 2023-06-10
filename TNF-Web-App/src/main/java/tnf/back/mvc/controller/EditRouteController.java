package tnf.back.mvc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tnf.back.db.entityes.User;
import tnf.back.db.repo.RouteRepository;

@Controller
public class EditRouteController {

    @Value("${upload.path}")
    private String uploadPath;
    private final RouteRepository repository;

    public EditRouteController(RouteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/editor/edit/{route_id}")
    public String openEditor(
            @AuthenticationPrincipal User user,
            @PathVariable("route_id") Long id,
            Model model
    ) {
        var route = repository.findById(id).get();
        model.addAttribute("isLoaded", true);
        model.addAttribute("loaded_name", route.getName());
        model.addAttribute("loaded_short_desc", route.getShortDescription());
        model.addAttribute("loaded_desc", route.getDescription());
        model.addAttribute("loaded_categories", route.getCategories());
        model.addAttribute("loaded_points", route.getPoints());
        return "create_route";
    }

}