package com.example.redis.demonstrations.streams;

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
        java.util.Map<String, String> value = message.getValue();
        if (value.containsKey("user") && value.containsKey("action")) {
            System.out.println("Received user action message: " + value + " with ID: " + message.getId());
        } else {
            System.out.println("Received generic stream message: " + value + " with ID: " + message.getId());
        }

        redisTemplate.opsForStream().acknowledge(RedisStreamConfig.STREAM_KEY, RedisStreamConfig.GROUP_NAME, message.getId());
        System.out.println("Acknowledged stream message: " + message.getId());
    }
}
