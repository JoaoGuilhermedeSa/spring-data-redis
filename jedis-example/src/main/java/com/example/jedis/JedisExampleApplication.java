package com.example.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.UUID;

public class JedisExampleApplication {

    public static void main(String[] args) throws InterruptedException {
        // Connect to Redis
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            System.out.println("---- Running Jedis Examples ----");
            demonstrateStringOperations(jedis);
            demonstrateHashOperations(jedis);
            demonstrateListOperations(jedis);
            demonstratePubSub(jedis);
            System.out.println("---- Examples Finished ----");
        } catch (Exception e) {
            System.err.println("Could not connect to Redis: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void demonstrateStringOperations(Jedis jedis) {
        System.out.println("\n---- Demonstrating String Operations ----");
        final String key = "jedis:string:name";
        jedis.set(key, "Jedis");
        String value = jedis.get(key);
        System.out.println("SET/GET: " + key + " -> " + value);
        jedis.del(key);
    }

    public static void demonstrateHashOperations(Jedis jedis) {
        System.out.println("\n---- Demonstrating Hash Operations ----");
        final String key = "jedis:hash:user";
        jedis.hset(key, "name", "Jane Doe");
        jedis.hset(key, "age", "25");
        String name = jedis.hget(key, "name");
        System.out.println("HSET/HGET: " + key + " -> name: " + name);
        jedis.del(key);
    }

    public static void demonstrateListOperations(Jedis jedis) {
        System.out.println("\n---- Demonstrating List Operations ----");
        final String key = "jedis:list:tasks";
        jedis.lpush(key, "Task 1", "Task 2");
        System.out.println("LPUSH to list: " + key);
        System.out.println("LRANGE from list: " + jedis.lrange(key, 0, -1));
        jedis.del(key);
    }

    public static void demonstratePubSub(Jedis jedis) throws InterruptedException {
        System.out.println("\n---- Demonstrating Pub/Sub ----");
        final String channel = "jedis-pubsub:queue";

        // Subscriber runs in a separate thread
        Thread subscriberThread = new Thread(() -> {
            try (Jedis subJedis = new Jedis("localhost", 6379)) {
                subJedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        System.out.println("Received << " + message + " >>");
                    }
                }, channel);
            }
        });
        subscriberThread.start();

        // Give the subscriber a moment to start
        Thread.sleep(100);

        // Publisher
        String message = "Message " + UUID.randomUUID();
        System.out.println("Publishing message: " + message);
        jedis.publish(channel, message);

        // Give subscriber time to receive the message and shut down
        Thread.sleep(1000);
        subscriberThread.interrupt();
    }
}
