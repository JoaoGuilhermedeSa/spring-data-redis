package com.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataRedisExampleApplication implements CommandLineRunner {

    @Autowired
    private RedisExampleService redisExampleService;

    @Autowired
    private CachingService cachingService;

    public static void main(String[] args) {
        SpringApplication.run(SpringDataRedisExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("---- Running Spring Data Redis Examples ----");
        redisExampleService.demonstrateStringOperations();
        redisExampleService.demonstrateHashOperations();
        redisExampleService.demonstrateListOperations();
        redisExampleService.demonstrateSetOperations();
        redisExampleService.demonstrateSortedSetOperations();
        redisExampleService.demonstrateRepositoryOperations();
        redisExampleService.demonstratePubSub();
        demonstrateCaching();
        System.out.println("---- Examples Finished ----");
    }

    private void demonstrateCaching() {
        System.out.println("\n---- Demonstrating Caching ----");

        System.out.println("First call to longRunningTask(2). Should take 2 seconds.");
        long start = System.currentTimeMillis();
        cachingService.longRunningTask(2);
        long end = System.currentTimeMillis();
        System.out.println("Call took " + (end - start) + "ms");

        System.out.println("\nSecond call to longRunningTask(2). Should be fast, from cache.");
        start = System.currentTimeMillis();
        cachingService.longRunningTask(2);
        end = System.currentTimeMillis();
        System.out.println("Call took " + (end - start) + "ms");
    }
}
