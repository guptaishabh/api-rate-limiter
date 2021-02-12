package com.example.apiratelimiter.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
