package com.example.redis.demonstrations.redistemplate;

import com.example.redis.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RedisTemplateDemonstration {

    private static final String PRICE_INDEX = "products:byPrice";
    private static final String PRODUCT_KEY_PREFIX = "product:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void run() {
        System.out.println("\n---- Demonstrating RedisTemplate Operations ----");

        // Create a product
        Product product = new Product("P999", "Gaming Monitor", 399.99);
        redisTemplate.opsForValue().set(PRODUCT_KEY_PREFIX + product.getId(), product);

        redisTemplate.opsForZSet().add(PRICE_INDEX, product.getId(), product.getPrice());
        System.out.println("Saved product using RedisTemplate: " + product);

        System.out.println("Fetching products between 100 and 500: ");

        Set<Object> ids = redisTemplate.opsForZSet().rangeByScore(PRICE_INDEX, 100, 500);

        Set<Product> products = getProducts(ids);
        System.out.println("Products are " + products);
    }

    private Set<Product> getProducts(Set<Object> ids) {
        if (ids == null) return Set.of();

        return ids.stream()
                .map(id -> (Product) redisTemplate.opsForValue().get(PRODUCT_KEY_PREFIX + id))
                .filter(p -> p != null)
                .collect(Collectors.toSet());
    }
}
