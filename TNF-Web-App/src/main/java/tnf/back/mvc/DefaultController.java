package tnf.back.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class DefaultController {

    @GetMapping("/index")
    public String def(Map<String, Object> model) {
        System.out.println("TESTOUT");
        return "index";
    }

}