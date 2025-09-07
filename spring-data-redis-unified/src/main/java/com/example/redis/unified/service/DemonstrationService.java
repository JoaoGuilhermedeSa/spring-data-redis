package com.example.redis.unified.service;

import com.example.redis.unified.model.Product;
import com.example.redis.unified.pubsub.RedisMessagePublisher;
import com.example.redis.unified.streams.StreamProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DemonstrationService {

    private static final String PRICE_INDEX = "products:byPrice";
    private static final String PRODUCT_KEY_PREFIX = "product:";

    @Autowired
    private CachingService cachingService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisMessagePublisher publisher;

    @Autowired
    private StreamProducerService streamProducerService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void demonstrateCaching() {
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

    public void demonstrateRepositoryOperations() {
        System.out.println("\n---- Demonstrating Repository Operations ----");

        // Create a new product
        Product product1 = new Product("P123", "Gaming Keyboard", 99.99);
        Product product2 = new Product("P456", "Gaming Mouse", 49.99);
        productService.save(product1);
        productService.save(product2);
        System.out.println("Saved products: " + product1 + ", " + product2);

        // Find all products
        System.out.println("Finding all products...");
        productService.findAll().forEach(p -> System.out.println("Found product: " + p));

        // Delete the products
        productService.deleteById("P123");
        productService.deleteById("P456");
        System.out.println("Deleted products.");
    }

    public void demonstrateIndexedField() {
        System.out.println("\n---- Demonstrating @Indexed Query ----");

        Product product1 = new Product("P789", "Webcam", 79.99);
        Product product2 = new Product("P101", "Microphone", 129.99);
        Product product3 = new Product("P112", "Webcam", 89.99);

        productService.save(product1);
        productService.save(product2);
        productService.save(product3);

        System.out.println("Finding products with name 'Webcam'...");
        List<Product> webcams = productService.findByName("Webcam");
        webcams.forEach(p -> System.out.println("Found product: " + p));

        productService.deleteById("P789");
        productService.deleteById("P101");
        productService.deleteById("P112");
        System.out.println("\nDeleted products.");
    }

    public void demonstratePubSub() throws InterruptedException {
        System.out.println("\n---- Demonstrating Pub/Sub ----");
        System.out.println("Publishing message: 'Hello, Redis!'");
        publisher.publish("Hello, Redis!");
        Thread.sleep(1000); // Wait for message to be received
    }

    public void demonstrateStreams() {
        System.out.println("\n---- Demonstrating Streams ----");
        Map<String, String> message = new HashMap<>();
        message.put("user", "Alice");
        message.put("action", "login");
        streamProducerService.sendMessage(message);
    }

    public void demonstrateRedisTemplateOperations() {
        System.out.println("\n---- Demonstrating RedisTemplate Operations ----");

        // Create a product
        Product product = new Product("P999", "Gaming Monitor", 399.99);
        redisTemplate.opsForValue().set(PRODUCT_KEY_PREFIX + product.getId(), product);

        redisTemplate.opsForZSet().add(PRICE_INDEX, product.getId(), product.getPrice());
        System.out.println("Saved product using RedisTemplate: " + product);

        System.out.println("Fetching products between 100 and 500: ");

        Set<Object> ids = redisTemplate.opsForZSet().rangeByScore(PRICE_INDEX, 100, 500);

        Set<Product> products = getProducts(ids);
        System.out.println("Products are " + products);
    }

    private Set<Product> getProducts(Set<Object> ids) {
               if (ids == null) return Set.of();

        return ids.stream()
                  .map(id -> (Product) redisTemplate.opsForValue().get(PRODUCT_KEY_PREFIX + id))
                  .filter(p -> p != null)
                  .collect(Collectors.toSet());
    }
}
