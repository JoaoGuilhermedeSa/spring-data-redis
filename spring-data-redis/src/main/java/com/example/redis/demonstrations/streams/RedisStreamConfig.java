package com.example.redis.demonstrations.streams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;

@Configuration
public class RedisStreamConfig {

    public static final String STREAM_KEY = "my-stream";
    public static final String GROUP_NAME = "my-group";

    @Autowired
    private StreamConsumerService streamConsumerService;

    @Bean
    public Subscription subscription(RedisConnectionFactory factory, RedisTemplate<String, Object> redisTemplate) {
        try {
            // Create the stream and group if they don't exist
            redisTemplate.opsForStream().createGroup(STREAM_KEY, ReadOffset.from("0-0"), GROUP_NAME);
        } catch (Exception e) {
            // Group might already exist, which is fine
        }

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions.builder().pollTimeout(Duration.ofSeconds(1)).build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> listenerContainer = StreamMessageListenerContainer.create(factory, options);

        Subscription subscription = listenerContainer.receive(Consumer.from(GROUP_NAME, "unified-consumer"),
                StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed()), streamConsumerService);

        listenerContainer.start();
        return subscription;
    }
}
