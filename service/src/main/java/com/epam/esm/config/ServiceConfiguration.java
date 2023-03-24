package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 Spring configuration class for service layer components.
 */
@Configuration
@ComponentScan(basePackages = "com.epam.esm")
public class ServiceConfiguration {
}
