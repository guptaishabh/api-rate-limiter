package com.example.apiratelimiter.storage.model;

public class RateLimitKeyUtils {

    public static final KeyGenerator GenerateFromRequest = (request) -> {
        Long ts = request.getEventTimestamp().toEpochMilli();
        Long burstMills = 10000L;

        return Double.toString(Math.ceil((double) ts / burstMills) * burstMills);
    };
}


