package com.example.apiratelimiter.storage.model;

import lombok.*;

import java.time.Duration;
import java.time.Instant;

@AllArgsConstructor
@Builder
@Getter
@ToString
@Data
public class RateLimitRequest {
    private Instant eventTimestamp;
    private String resourceKey;
    private int capacity;
    private Duration expiration;
}
