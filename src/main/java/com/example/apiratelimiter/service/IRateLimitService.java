package com.example.apiratelimiter.service;

import com.example.apiratelimiter.exception.RateLimitExceededException;

public interface IRateLimitService {

    void call(String userKey, String resourceName)
            throws RateLimitExceededException;
}
