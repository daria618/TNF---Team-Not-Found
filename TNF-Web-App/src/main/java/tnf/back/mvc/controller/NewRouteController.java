package tnf.back.mvc.controller;

import org.springframework.beans.factory.annotation.Value;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@Controller
public class NewRouteController {

    @Value("${upload.path}")
    private String uploadPath;
    private final RouteRepository repository;

    public NewRouteController(RouteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/editor/create")
    public String open(Model model){
        model.addAttribute("isLoaded", false);
        return "create_route";
    }

    @PostMapping(value = "/editor/create/fin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveRoute(
            @AuthenticationPrincipal User user,
            @RequestParam Map<String, Object> formData,
            @RequestParam("img_main") MultipartFile imageFile,
            @RequestParam("img_other") MultipartFile[] otherImgFile
    ) throws IOException {
        ArrayList<String> keys = new ArrayList<>(formData.keySet());

        Route route;
        boolean isLoaded = false;
        if (!keys.contains("routeID"))
            route = new Route();
        else{
            route = repository.findById(Long.parseLong(formData.get("routeID").toString())).get();
            isLoaded = true;
        }

        if (!isLoaded)
            route.setAuthor(user);

        route.setCategories(null);

        if (!imageFile.isEmpty()){
            String path = UUID.randomUUID() + imageFile.getOriginalFilename();
            Files.write(Paths.get(uploadPath + "/" + path), imageFile.getBytes());
            route.setImageName(path);
        }
        else if (!isLoaded)
            route.setImageName(null);

        HashSet<String> strings = new HashSet<>();
        for (MultipartFile multipartFile : otherImgFile) {
            if (!multipartFile.isEmpty()) {
                String pathOther = UUID.randomUUID() + multipartFile.getOriginalFilename();
                strings.add(pathOther);
                Files.write(Paths.get(uploadPath + "/" + pathOther), multipartFile.getBytes());
            }
        }
        if (isLoaded){
            if (strings.size() > 0)
                route.setAddImages(strings);
        }
        else route.setAddImages(strings.size() > 0 ? strings : null);

        for (String key : keys) {
            switch (key) {
                case "route_name" -> route.setName(formData.get(key).toString());
                case "desc_short" -> route.setShortDescription(formData.get(key).toString());
                case "desc_long" -> route.setDescription(formData.get(key).toString());
            }
        }

        ArrayList<MapPoint> points = new ArrayList<>();
        int lastInd = Integer.MIN_VALUE;
        for (String key : keys) {
            String[] parts = key.split("_");
            if (parts[0].equals("pointData")) {
                int ind = Integer.parseInt(parts[1]);

                if (lastInd != ind) {
                    points.add(new MapPoint());
                    lastInd = ind;
                }

                switch (parts[2]) {
                    case "coord" -> {
                        if (parts[3].equals("lat"))
                            points.get(points.size() - 1).setLatitude(formData.get(key).toString());
                        else if (parts[3].equals("lon"))
                            points.get(points.size() - 1).setLongitude(formData.get(key).toString());
                    }
                    case "adress" -> points.get(points.size() - 1).setTextRepresent(formData.get(key).toString());
                    case "desc" -> points.get(points.size() - 1).setDescription(formData.get(key).toString());
                }
            }
        }

        if (!isLoaded)
            route.setPoints(points);

        System.out.println(route);

        repository.save(route);

        return "redirect:/routes";
    }

}