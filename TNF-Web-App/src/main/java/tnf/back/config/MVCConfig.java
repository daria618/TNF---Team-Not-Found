package tnf.back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MVCConfig implements WebMvcConfigurer {
    public void addNewControllers(ViewControllerRegistry registry){
//        registry.addViewController("/index").setViewName("index");
    }
}