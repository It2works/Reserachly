package com.example.demo.config; // عدلها حسب الباكج متاعك

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // كل المسارات
                        .allowedOrigins("http://localhost:4200") // frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // طرق الاتصال المسموحة
                        .allowedHeaders("*") // كل الهيدرز
                        .allowCredentials(true); // يسمح بإرسال الكوكيز والتوكنز
            }
        };
    }
}
