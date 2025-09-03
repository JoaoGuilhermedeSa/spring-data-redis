package com.example.redis.streams;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;

@Configuration
public class RedisConfig {

    public static final String STREAM_KEY = "my-stream";
    public static final String GROUP_NAME = "my-group";

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    public StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ?> streamMessageListenerContainerOptions() {
        return StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .build();
    }

    @Bean
    public StreamMessageListenerContainer<String, ?> streamMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ?> options,
            StreamConsumerService streamConsumerService) {

        StreamMessageListenerContainer<String, ?> container = StreamMessageListenerContainer.create(connectionFactory, options);

        try {
            // Try to create the consumer group. This will fail if the group already exists, which is fine.
            connectionFactory.getConnection().xGroupCreate(STREAM_KEY.getBytes(), GROUP_NAME, ReadOffset.latest(), true);
        } catch (Exception e) {
            System.out.println("Consumer group already exists or stream does not exist yet.");
        }

        Subscription subscription = container.receive(
                Consumer.from(GROUP_NAME, "consumer-1"),
                StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed()),
                streamConsumerService
        );

        container.start();
        return container;
    }
}
