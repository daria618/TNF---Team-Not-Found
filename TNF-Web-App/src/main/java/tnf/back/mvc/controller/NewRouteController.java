package tnf.back.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NewRouteController {

    @GetMapping("/editor/create")
    public String openCreation(){
        return "temp_route_editor";
    }

    @RequestMapping(value = "/route/create/tryAdd", method = RequestMethod.POST)
    public String acceptForm(){
        return "/home";
    }

}