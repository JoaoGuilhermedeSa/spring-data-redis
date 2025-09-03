package com.example.redis;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachingService {

    @Cacheable(value = "longRunningTaskCache", key = "#seconds")
    public String longRunningTask(int seconds) {
        try {
            System.out.println("Executing long running task...");
            Thread.sleep(seconds * 1000);
            return "Task finished.";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Task interrupted.";
        }
    }
}
