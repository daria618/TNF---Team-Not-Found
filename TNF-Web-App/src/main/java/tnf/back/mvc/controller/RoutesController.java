package tnf.back.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tnf.back.db.entityes.RatingMark;
import tnf.back.db.entityes.Route;
import tnf.back.db.repo.RatingRepository;
import tnf.back.db.repo.RouteRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoutesController {

    private final RouteRepository repository;
    private final RatingRepository ratingRepository;

    public RoutesController(
            RouteRepository repository,
            RatingRepository ratingRepository
    ) {
        this.repository = repository;
        this.ratingRepository = ratingRepository;
    }

    @GetMapping("/routes")
    public String openRoutes(Model model) {
        var routes = repository.findAll();
        model.addAttribute("routes", routes);
        model.addAttribute("test", "TEST");
        model.addAttribute("topRated", sortByRating(new ArrayList<>(routes)));
        return "routes";
    }

    private Route[] sortByRating(ArrayList<Route> routes) {
        if (routes.size() == 0) return null;

        Route[] routeArray = new Route[routes.size()];
        for (int i = 0; i < routes.size(); i++) routeArray[i] = routes.get(i);

        if (routeArray.length <= 3){
            return routeArray;
        }
        else{
            double[] ratingValues = new double[routeArray.length];
            for (int i = 0; i < ratingValues.length; i++) {
                double sum = 0;
                int count = 0;
                for (var mark : ratingRepository.findByRoute(routeArray[i])) {
                    sum += mark.getValue();
                    count += 1;
                }
                if (count > 0) sum /= count;
                ratingValues[i] = sum;
            }

            for (int i = 0; i < ratingValues.length - 1; i++) {
                for (int j = 0; j < ratingValues.length - 1; j++) {
                    if (ratingValues[j] > ratingValues[j + 1]) {
                        var rb = ratingValues[j];
                        ratingValues[j] = ratingValues[j + 1];
                        ratingValues[j + 1] = rb;
                        var rr = routeArray[j];
                        routeArray[j] = routeArray[j + 1];
                        routeArray[j + 1] = rr;
                    }
                }
            }

            return new Route[]{routeArray[0], routeArray[1], routeArray[2]};
        }
    }
}