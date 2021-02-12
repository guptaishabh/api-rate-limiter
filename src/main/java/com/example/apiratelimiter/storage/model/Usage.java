package com.example.apiratelimiter.storage.model;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * To Store Usage and Small Interval as Memory Efficient Solution
 */
@Data
public class Usage {
    private String epochKey;
    private AtomicInteger current;

    public Usage(String epochKey) {
        this.epochKey = epochKey;
        this.current = new AtomicInteger(0);
    }

    public Integer incWithinLimit(int limit) {
        return current.accumulateAndGet(1, (left, right) -> left > limit ? left : left + right);
    }

    public Integer getCurrentUsage() {
        return current.get();
    }

    public String getEpochKey() {
        return this.epochKey;
    }
}
