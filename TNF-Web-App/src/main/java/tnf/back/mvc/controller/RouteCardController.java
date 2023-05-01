package tnf.back.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tnf.back.db.repo.RouteRepository;

@Controller
public class RouteCardController {

    private final RouteRepository routeRepository;

    public RouteCardController(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @GetMapping("/routes/{route_id}")
    public String open(@PathVariable("route_id") Long id, Model model){
        var route = routeRepository.findById(id).get();
        model.addAttribute("route", route);
        return "temp_route_card";
    }

}
