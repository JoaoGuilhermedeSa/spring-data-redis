package com.example.redis.demonstrations.streams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
public class StreamProducerService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String sendMessage(@RequestBody Map<String, String> message) {
        ObjectRecord<String, Map<String, String>> record = StreamRecords.newRecord()
                .in(RedisStreamConfig.STREAM_KEY)
                .ofObject(message);

        redisTemplate.opsForStream().add(record);
        System.out.println("Sent message: " + message);
        return "Message sent!";
    }
}