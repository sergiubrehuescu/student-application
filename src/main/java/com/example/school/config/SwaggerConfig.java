package com.example.school.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

@Configuration
@EnableSwagger2
@Profile(value = "dev")
public class SwaggerConfig {

    @PostConstruct
    private void show(){
        System.out.println("Swagger is running on developement phase");
    }

}
