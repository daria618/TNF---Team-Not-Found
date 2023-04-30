package tnf.back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MVCConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("temp_home");
        registry.addViewController("/").setViewName("temp_home");
        registry.addViewController("/hello").setViewName("temp_hello");
        registry.addViewController("/login").setViewName("temp_login");
    }
}