package tnf.back.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tnf.back.db.repo.RouteRepository;

import java.util.ArrayList;

@Controller
public class CatalogController {

    private final RouteRepository repository;

    public CatalogController(RouteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/routes")
    public String openRoutes(Model model){

        var routes = repository.findAll();

        model.addAttribute("routes", routes);

        return "temp_catalog";
    }

}