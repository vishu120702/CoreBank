package com.vishu.project.corebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorebankApplication {
    public static void main(String[] args) {
        SpringApplication.run(CorebankApplication.class, args);
        System.out.println("Working Fine");
    }
}