# Caching Demonstration

This package contains a demonstration of Redis caching using Spring's `@Cacheable` annotation.

The `CachingDemonstration` service shows how to:
- Call a method for the first time, which will be slow as the result is not in the cache.
- Call the same method again, which will be fast as the result is retrieved from the Redis cache.


# Output:
---- Demonstrating Caching ----

First call to getProducts(). Should take 1 second.
Call took 1095ms

Second call to longRunningTask(2). Should be fast, from cache.
Call took 28ms