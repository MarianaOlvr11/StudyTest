package com.example.studyy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class StudyyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyyApplication.class, args);
    }

}
