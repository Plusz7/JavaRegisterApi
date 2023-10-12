package com.myproject.registerApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@PropertySource("classpath:application.properties")
public class RegisterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterApiApplication.class, args);
    }

}
