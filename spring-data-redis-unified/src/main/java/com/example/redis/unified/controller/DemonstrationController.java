package com.example.redis.unified.controller;

import com.example.redis.unified.service.DemonstrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demonstrate")
public class DemonstrationController {

    @Autowired
    private DemonstrationService demonstrationService;

    @PostMapping("/caching")
    public String demonstrateCaching() {
        demonstrationService.demonstrateCaching();
        return "Caching demonstration finished.";
    }

    @PostMapping("/repository")
    public String demonstrateRepository() {
        demonstrationService.demonstrateRepositoryOperations();
        return "Repository demonstration finished.";
    }

    @PostMapping("/indexed-field")
    public String demonstrateQuery() {
        demonstrationService.demonstrateIndexedField();
        return "Indexed field demonstration finished.";
    }

    @PostMapping("/pubsub")
    public String demonstratePubSub() throws InterruptedException {
        demonstrationService.demonstratePubSub();
        return "Pub/Sub demonstration finished.";
    }

    @PostMapping("/streams")
    public String demonstrateStreams() {
        demonstrationService.demonstrateStreams();
        return "Streams demonstration finished.";
    }

    @PostMapping("/redis-template")
    public String demonstrateRedisTemplate() {
        demonstrationService.demonstrateRedisTemplateOperations();
        return "RedisTemplate demonstration finished.";
    }
}
