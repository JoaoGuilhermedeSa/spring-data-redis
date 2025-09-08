package com.example.redis.demonstrations.caching;

import com.example.redis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CachingDemonstration {

    @Autowired
    private ProductService productService;

    public void run() {
        System.out.println("\n---- Demonstrating Caching ----");

        // Step 1: Call a method that is cached.
        // The first call will be slow as the result is not in the cache.
        System.out.println("First call to findAllWithDelay(). Should take 1 second.");
        long start = System.currentTimeMillis();
        productService.findAllWithDelay();
        long end = System.currentTimeMillis();
        System.out.println("Call took " + (end - start) + "ms");

        // Step 2: Call the same method again with the same argument.
        // The second call should be much faster as the result is retrieved from the cache.
        System.out.println("\nSecond call to findAllWithDelay(). Should be fast, from cache.");
        start = System.currentTimeMillis();
        productService.findAllWithDelay();
        end = System.currentTimeMillis();
        System.out.println("Call took " + (end - start) + "ms");
    }
}
