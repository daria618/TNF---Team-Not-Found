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
    public String open(){
        return "create_route";
    }

    @PostMapping(value = "/editor/create/fin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveRoute(
            @AuthenticationPrincipal User user,
            @RequestParam Map<String, Object> formData,
            @RequestParam("img_main") MultipartFile imageFile,
            @RequestParam("img_other") MultipartFile[] otherImgFile,
            Model model
    ) throws IOException {
        Route route = new Route();
        route.setAuthor(user);
        route.setRating(0);
        route.setCategories(null);

        String path = UUID.randomUUID() + imageFile.getOriginalFilename();
        Files.write(Paths.get(uploadPath + "/" + path), imageFile.getBytes());

        StringBuilder mainImgPath = new StringBuilder();
        for (var i = 0; i < otherImgFile.length; i++){
            String pathOther = UUID.randomUUID() + otherImgFile[i].getOriginalFilename();
            mainImgPath.append(mainImgPath).append(i >= otherImgFile.length - 1 ? "" : "_");
            Files.write(Paths.get(uploadPath + "/" + pathOther), otherImgFile[i].getBytes());
        }
        route.setImageName(path);
        route.setAddImages(mainImgPath.toString());

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

//        System.out.println(route.getName());
//        System.out.println(route.getShortDescription());
//        System.out.println(route.getDescription());
//        for (var e : route.getPoints())
//            System.out.println(e.getStr());

        repository.saveAndFlush(route);

        return "redirect:/home";
    }

}