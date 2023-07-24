package com.example.walkingmate_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WalkingMateBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalkingMateBackApplication.class, args);
    }

}
