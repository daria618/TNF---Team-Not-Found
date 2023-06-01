package tnf.back.mvc.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tnf.back.db.entityes.MapPoint;
import tnf.back.db.entityes.Route;
import tnf.back.db.entityes.User;
import tnf.back.db.repo.RouteRepository;

import java.util.ArrayList;
import java.util.Map;

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
            @RequestParam Map<String, Object> formData,
            Model model
    ){
        Route route = new Route();
        route.setAuthor(user);
        route.setRating(0);
        route.setCategories(null);

        ArrayList<String> keys = new ArrayList<>(formData.keySet());
        ArrayList<MapPoint> points = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++){
            System.out.println(keys.get(i) + " : " + formData.get(keys.get(i)).toString());
            if (keys.get(i).equals("route_name"))
                route.setName(formData.get(keys.get(i)).toString());
            else if (keys.get(i).equals("desc_short"))
                route.setShortDescription(formData.get(keys.get(i)).toString());
            else if (keys.get(i).equals("desc_long"))
                route.setDescription(formData.get(keys.get(i)).toString());
            else{
                String[] parts = keys.get(i).split("_");
                if (parts[0].equals("buttons")){
                    if (parts[2].equals("adress")){
                        points.add(new MapPoint(null, null, formData.get(keys.get(i)).toString()));
                    }
                    else if (parts[2].equals("coord")){
                        points.add(new MapPoint(
                                formData.get(keys.get(i)).toString(),
                                formData.get(keys.get(i + 1)).toString(),
                                null
                        ));
                        i++;
                    }
                }
            }
        }
        route.setPoints(points);

        System.out.println(route.getName());
        System.out.println(route.getShortDescription());
        System.out.println(route.getDescription());
        for (var e : route.getPoints())
            System.out.println(e.getStr());

        return "redirect:/home";
    }

}