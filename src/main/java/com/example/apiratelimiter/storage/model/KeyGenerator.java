package com.example.apiratelimiter.storage.model;

public interface KeyGenerator {
    String getKey(RateLimitRequest request);
}
