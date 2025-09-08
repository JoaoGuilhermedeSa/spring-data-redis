package com.example.redis.controller;

import com.example.redis.demonstrations.caching.CachingDemonstration;
import com.example.redis.demonstrations.pubsub.PubSubDemonstration;
import com.example.redis.demonstrations.redistemplate.RedisTemplateDemonstration;
import com.example.redis.demonstrations.repository.RepositoryDemonstration;
import com.example.redis.demonstrations.streams.StreamsDemonstration;
import com.example.redis.model.Product;
import com.example.redis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demonstrate")
public class DemonstrationController {

    @Autowired private CachingDemonstration cachingDemonstration;
    @Autowired private RepositoryDemonstration repositoryDemonstration;
    @Autowired private PubSubDemonstration pubSubDemonstration;
    @Autowired private StreamsDemonstration streamsDemonstration;
    @Autowired private RedisTemplateDemonstration redisTemplateDemonstration;
    @Autowired private ProductService productService;


    @PostMapping("/caching")
    public String demonstrateCaching() {
        cachingDemonstration.run();
        return "Caching demonstration finished. Check the console for output.";
    }

    @PostMapping("/repository")
    public String demonstrateRepository() {
        repositoryDemonstration.run();
        return "Repository demonstration finished. Check the console for output.";
    }

    @PostMapping("/pubsub")
    public String demonstratePubSub() throws InterruptedException {
        pubSubDemonstration.run();
        return "Pub/Sub demonstration finished. Check the console for output.";
    }

    @PostMapping("/streams")
    public String demonstrateStreams() {
        streamsDemonstration.run();
        return "Streams demonstration finished. Check the console for output.";
    }

    @PostMapping("/redis-template")
    public String demonstrateRedisTemplate() {
        redisTemplateDemonstration.run();
        return "RedisTemplate demonstration finished. Check the console for output.";
    }

    // This endpoint seems to be using ProductService directly, so I'll keep it.
    @PostMapping("/cached-products")
    public Iterable<Product> demonstrateCachedProducts() {
        return productService.findAllWithDelay();
    }
}