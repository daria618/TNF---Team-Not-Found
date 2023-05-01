package tnf.back.mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tnf.back.db.entityes.MapPoint;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class NewRouteController {

    @GetMapping("/editor/create")
    public String OpenEditorCreate(){
        return "temp_route_editor";
    }

    @GetMapping("/editor/edit")
    public String OpenEditorEdit(Model model){
        return "temp_route_editor";
    }

    @RequestMapping(value = "/editor/create", method = RequestMethod.POST)
    public String acceptForm(HttpServletRequest request, Model model){
        Map<String, String[]> parameterMap = request.getParameterMap();

        ArrayList<MapPoint> points = new ArrayList<>(16);

        StringBuilder builder = new StringBuilder();

        parameterMap.forEach((key, value) -> {
            if (!key.equals("_csrf")){
                String[] keyParts = key.split("_");
                if (keyParts.length == 4){
                    int index = Integer.parseInt(keyParts[3]);
                    if (index >= points.size()) points.add(new MapPoint());
                    switch (keyParts[1]){
                        case "name" -> points.get(index).setTextRepresent(value[0]);
                        case "lat" -> points.get(index).setLatitude(value[0]);
                        case "lon" -> points.get(index).setLongitude(value[0]);
                    }
                }
                builder.append(key).append(" = ").append(value[0]).append("   |   ");
            }
        });

        model.addAttribute("message", builder.toString());
        model.addAttribute("points", points);

        return "temp_route_editor";
    }



}