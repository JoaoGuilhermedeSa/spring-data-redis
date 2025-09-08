package com.example.redis.demonstrations.pubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PubSubDemonstration {

    @Autowired
    private RedisMessagePublisher publisher;

    public void run() throws InterruptedException {
        System.out.println("\n---- Demonstrating Pub/Sub ----");

        System.out.println("Publishing message: 'Hello, Redis!'");
        publisher.publish("Hello, Redis!");

        Thread.sleep(1000);
    }
}
