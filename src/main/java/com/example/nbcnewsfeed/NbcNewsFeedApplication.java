package com.example.nbcnewsfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NbcNewsFeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(NbcNewsFeedApplication.class, args);
    }

}
