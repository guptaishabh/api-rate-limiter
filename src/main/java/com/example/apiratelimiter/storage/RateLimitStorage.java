package com.example.apiratelimiter.storage;

import com.example.apiratelimiter.storage.model.RateLimitRequest;

public interface RateLimitStorage<T> {

    /**
     * Tries to increment the count under capacity provided with expiration for the given resource.
     * Throws an Exception when limit is exceeded
     *
     * @param limitRequest - Rate Limiting Request
     */
    T addAndGetWithinLimit(RateLimitRequest limitRequest);

    /**
     * Used for closing the storage when the usage is completed
     *
     * @throws Exception - When closing the storage fails
     */
    void close() throws Exception;

    /**
     * Remove if present
     * @param request
     */
    void remove(RateLimitRequest request);
}
