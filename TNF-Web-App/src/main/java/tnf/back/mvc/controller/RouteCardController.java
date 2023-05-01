package tnf.back.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tnf.back.db.entityes.MapPoint;
import tnf.back.db.repo.RouteRepository;
import tnf.back.logic.Checker;

import java.util.ArrayList;

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
        var texts = new ArrayList<String>();
        for (var point : route.getPoints()) texts.add(getMapPointStr(point));
        model.addAttribute("texts", texts);
        return "temp_route_card";
    }

    private String getMapPointStr(MapPoint point){
        ArrayList<String> strings = new ArrayList<>();

        if (!Checker.isEmptyString(point.getLatitude()))
            strings.add(point.getLatitude());
        if (!Checker.isEmptyString(point.getLongitude()))
            strings.add(point.getLongitude());
        if (!Checker.isEmptyString(point.getTextRepresent()))
            strings.add(point.getTextRepresent());

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strings.size(); i++){
            result.append(strings.get(i));
            if (i != strings.size() - 1)
                result.append("|");
        }
        return result.toString();
    }

}
