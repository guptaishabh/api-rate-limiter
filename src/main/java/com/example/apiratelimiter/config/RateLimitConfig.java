package com.example.apiratelimiter.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class RateLimitConfig {
    private String name;
    private int capacity;
    private Duration expiration;
}
