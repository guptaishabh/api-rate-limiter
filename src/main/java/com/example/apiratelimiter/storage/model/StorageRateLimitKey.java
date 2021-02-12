package com.example.apiratelimiter.storage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;

@Getter
@Data
@ToString
@AllArgsConstructor
public class StorageRateLimitKey {
    private String key;
    private int capacity;
    private Duration expiration;

    public static StorageRateLimitKey fromRequest(RateLimitRequest rateLimitRequest) {
        return new StorageRateLimitKey(rateLimitRequest.getResourceKey(), rateLimitRequest.getCapacity(), rateLimitRequest.getExpiration());
    }
}
