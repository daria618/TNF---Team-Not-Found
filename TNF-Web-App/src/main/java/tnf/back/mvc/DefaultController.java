package tnf.back.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class DefaultController {

    @GetMapping("/hello")
    public String hello(Model model){
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) list.add(String.valueOf('A' + i));

        model.addAttribute("items", list);

        return "/temp_hello";
    }

}