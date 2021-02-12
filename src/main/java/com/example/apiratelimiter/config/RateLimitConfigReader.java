package com.example.apiratelimiter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
public class RateLimitConfigReader {

    public RateLimitConfig read(String resourceName) {
        return new RateLimitConfig(resourceName, 100, Duration.of(1, ChronoUnit.HOURS));
    }
}
