package com.example.hostel_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages="com.example.hostel_service")
@EnableJpaRepositories(basePackages = "com.example.hostel_service.app.output.repository")
@EntityScan(basePackages = "com.example.hostel_service.app.output.entity")
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
