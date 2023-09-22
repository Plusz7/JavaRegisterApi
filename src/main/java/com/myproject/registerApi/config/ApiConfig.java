package com.myproject.registerApi.config;

import com.myproject.registerApi.repository.ActivityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ApiConfig {

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate();
    }

//    @Bean
//    public ActivityRepository activityRepository(JdbcTemplate jdbcTemplate) {
//        return new ActivityRepository(jdbcTemplate);
//    }
}
