package tnf.back.mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tnf.back.db.entityes.Role;
import tnf.back.db.entityes.Route;
import tnf.back.db.entityes.User;

import java.util.Collections;
import java.util.Map;

@Controller
public class NewRouteController {

    private final Route testRoute = new Route(
            "TEST_ROUTE",
            "TSD",
            "TD",
            new User("TESTUSER", "TESTPASSWORD", "TESTEMAIL", true, Collections.singleton(Role.USER)),
            0,
            "NULL"
    );

    @GetMapping("/editor/create")
    public String OpenEditorCreate(){
        return "temp_route_editor";
    }

    @GetMapping("/editor/edit")
    public String OpenEditorEdit(Model model){
        return "temp_route_editor";
    }

    @RequestMapping(value = "/editor/create/tryAdd", method = RequestMethod.POST)
    public String acceptForm(HttpServletRequest request, Model model){
        Map<String, String[]> parameterMap = request.getParameterMap();

        StringBuilder builder = new StringBuilder();

        parameterMap.forEach((key, value) -> {
            if (!key.equals("_csrf"))
                builder.append(key).append(" = ").append(value[0]).append("\n");
        });

        model.addAttribute("sometext", builder.toString());

        return "redirect:/editor/edit";
    }

}