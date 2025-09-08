package com.example.redis.demonstrations.pubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PubSubDemonstration {

    @Autowired
    private RedisMessagePublisher publisher;

    public void run() throws InterruptedException {
        System.out.println("\n---- Demonstrating Pub/Sub ----");

        // Step 1: Publish a message to a Redis channel.
        // A subscriber is listening for messages on this channel and will print the message to the console.
        System.out.println("Publishing message: 'Hello, Redis!'");
        publisher.publish("Hello, Redis!");

        // Step 2: Wait for a moment to allow the message to be received by the subscriber.
        Thread.sleep(1000);
    }
}
