package tnf.back.mvc.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tnf.back.db.entityes.MapPoint;
import tnf.back.db.entityes.Route;
import tnf.back.db.entityes.RouteCategory;
import tnf.back.db.entityes.User;
import tnf.back.db.repo.RouteRepository;
import tnf.back.logic.Transform;

import java.util.ArrayList;
import java.util.Collections;

@Controller
public class NewRouteController {

    private final RouteRepository repository;

    private final ArrayList<MapPoint> points = new ArrayList<>();

    public NewRouteController(RouteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/editor/create")
    public String OpenEditorCreate(){
        return "create_route";
    }

    @PostMapping("/editor/create/add")
    public String addRec(
            @RequestParam(name = "lon") String lon,
            @RequestParam(name = "lat") String lat,
            @RequestParam(name = "name") String name,
            Model model
    ){
        MapPoint point = new MapPoint(lat, lon, name);
        points.add(point);

        model.addAttribute("points", points);
        model.addAttribute("texts", Transform.MapPointToYMAPString(points));
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
        Route route = new Route(name, short_description, description, user, 0L, null, points, Collections.singleton(RouteCategory.SOLO));
        repository.saveAndFlush(route);

        model.addAttribute("points", points);
        return "redirect:/routes";
    }

}