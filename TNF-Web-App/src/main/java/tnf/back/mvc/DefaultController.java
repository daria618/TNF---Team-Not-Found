package tnf.back.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class DefaultController {

    @GetMapping("/dev")
    public String greeting(Map<String, Object> model) {
        return "index";
    }

}