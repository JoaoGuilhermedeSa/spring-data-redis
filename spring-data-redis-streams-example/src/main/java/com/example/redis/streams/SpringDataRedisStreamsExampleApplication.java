package com.example.redis.streams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringDataRedisStreamsExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataRedisStreamsExampleApplication.class, args);
    }

}
