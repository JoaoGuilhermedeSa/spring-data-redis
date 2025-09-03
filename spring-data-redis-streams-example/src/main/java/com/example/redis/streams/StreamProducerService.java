package com.example.redis.streams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StreamProducerService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/send")
    public String sendMessage(@RequestBody Map<String, String> message) {
        ObjectRecord<String, Map<String, String>> record = StreamRecords.newRecord()
                .in(RedisConfig.STREAM_KEY)
                .ofObject(message);

        redisTemplate.opsForStream().add(record);
        System.out.println("Sent message: " + message);
        return "Message sent!";
    }
}
