package com.example.redis.demonstrations.streams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StreamsDemonstration {

    @Autowired
    private StreamProducerService streamProducerService;

    public void run() {
        System.out.println("\n---- Demonstrating Streams ----");

        // Step 1: Send a message to a Redis Stream.
        // A stream consumer is listening for messages and will print the message to the console.
        System.out.println("Sending a message to the stream...");
        Map<String, String> message = new HashMap<>();
        message.put("user", "Alice");
        message.put("action", "login");
        streamProducerService.sendMessage(message);
        System.out.println("Message sent.");
    }
}
