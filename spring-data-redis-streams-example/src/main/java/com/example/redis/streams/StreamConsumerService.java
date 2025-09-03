package com.example.redis.streams;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class StreamConsumerService implements StreamListener<String, MapRecord<String, String, String>> {

    private final RedisTemplate<String, Object> redisTemplate;

    public StreamConsumerService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        System.out.println("Received message: " + message.getValue() + " with ID: " + message.getId());

        // Acknowledge the message
        redisTemplate.opsForStream().acknowledge(RedisConfig.STREAM_KEY, RedisConfig.GROUP_NAME, message.getId());
        System.out.println("Acknowledged message: " + message.getId());
    }
}
