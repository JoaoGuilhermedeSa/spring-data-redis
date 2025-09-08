# RedisTemplate Operations Demonstration

This package demonstrates direct Redis operations using Spring's `RedisTemplate`.

The `RedisTemplateDemonstration` service shows how to:
- Store a Java object (Product) as a Redis Hash using `opsForHash()`.
- Retrieve the stored object.
- Delete the object from Redis.

# Output:

---- Demonstrating RedisTemplate Operations ----

Saved product using RedisTemplate: Product{id='P999', name='Gaming Monitor', price=399.99}

Fetching products between 100 and 500:

Products are [Product{id='P999', name='Gaming Monitor', price=399.99}]
