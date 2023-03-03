package com.epam.esm.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
public class WebConfiguration extends WebMvcConfigurationSupport {
    @Bean
    public Gson gson() {
        return new Gson();
    }
}