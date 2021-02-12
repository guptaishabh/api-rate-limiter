package com.example.apiratelimiter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Slf4j
public class RateLimitConfigReader {

    @Value("${rate-limit.capacity}")
    private int capacity;

    @Value("${rate-limit.expiration}")
    private Duration expiration;

    public RateLimitConfig read(String resourceName) {
        return new RateLimitConfig(resourceName, this.capacity, this.expiration);
    }
}
