package com.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RedisExampleService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ChannelTopic topic;

    @Autowired
    private ProductRepository productRepository;

    public void demonstrateStringOperations() {
        System.out.println("\n---- Demonstrating String Operations ----");
        final String key = "spring:string:name";
        redisTemplate.opsForValue().set(key, "Spring Data Redis");
        String value = (String) redisTemplate.opsForValue().get(key);
        System.out.println("SET/GET: " + key + " -> " + value);
        redisTemplate.delete(key);
    }

    public void demonstrateHashOperations() {
        System.out.println("\n---- Demonstrating Hash Operations ----\n");
        final String key = "spring:hash:user";
        redisTemplate.opsForHash().put(key, "name", "John Doe");
        redisTemplate.opsForHash().put(key, "age", "30");
        String name = (String) redisTemplate.opsForHash().get(key, "name");
        System.out.println("HSET/HGET: " + key + " -> name: " + name);
        redisTemplate.delete(key);
    }

    public void demonstrateListOperations() {
        System.out.println("\n---- Demonstrating List Operations ----");
        final String key = "spring:list:tasks";
        redisTemplate.opsForList().leftPush(key, "Task 1");
        redisTemplate.opsForList().leftPush(key, "Task 2");
        System.out.println("LPUSH to list: " + key);
        System.out.println("LRANGE from list: " + redisTemplate.opsForList().range(key, 0, -1));
        redisTemplate.delete(key);
    }

    public void demonstratePubSub() throws InterruptedException {
        System.out.println("\n---- Demonstrating Pub/Sub ----");
        String message = "Message " + UUID.randomUUID();
        System.out.println("Publishing message: " + message);
        redisTemplate.convertAndSend(topic.getTopic(), message);
        // Give subscriber time to receive the message
        Thread.sleep(1000);
    }

    public void demonstrateSetOperations() {
        System.out.println("\n---- Demonstrating Set Operations ----");
        final String key = "spring:set:developers";
        redisTemplate.opsForSet().add(key, "Alice", "Bob", "Charlie");
        System.out.println("SADD to set: " + key);
        System.out.println("SMEMBERS from set: " + redisTemplate.opsForSet().members(key));
        System.out.println("SISMEMBER 'Alice': " + redisTemplate.opsForSet().isMember(key, "Alice"));
        redisTemplate.delete(key);
    }

    public void demonstrateSortedSetOperations() {
        System.out.println("\n---- Demonstrating Sorted Set Operations ----");
        final String key = "spring:zset:leaderboard";
        redisTemplate.opsForZSet().add(key, "PlayerOne", 100);
        redisTemplate.opsForZSet().add(key, "PlayerTwo", 85);
        redisTemplate.opsForZSet().add(key, "PlayerThree", 120);
        System.out.println("ZADD to sorted set: " + key);
        System.out.println("ZRANGE from sorted set: " + redisTemplate.opsForZSet().range(key, 0, -1));
        System.out.println("ZREVRANGE from sorted set: " + redisTemplate.opsForZSet().reverseRange(key, 0, -1));
        redisTemplate.delete(key);
    }

    public void demonstrateRepositoryOperations() {
        System.out.println("\n---- Demonstrating Repository Operations ----");

        // Create a new product
        Product product = new Product("P123", "Gaming Keyboard", 99.99);
        productRepository.save(product);
        System.out.println("Saved product: " + product);

        // Find a product by ID
        System.out.println("Finding product with ID P123...");
        productRepository.findById("P123").ifPresent(p -> System.out.println("Found product: " + p));

        // Delete the product
        productRepository.deleteById("P123");
        System.out.println("Deleted product with ID P123.");
    }
}
