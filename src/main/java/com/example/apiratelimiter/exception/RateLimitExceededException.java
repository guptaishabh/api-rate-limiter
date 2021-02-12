package com.example.apiratelimiter.exception;

import lombok.Getter;

@Getter
public class RateLimitExceededException extends Exception {

    private Long waitForMs;

    public RateLimitExceededException(String message) {
        super(message);
    }

    public RateLimitExceededException(String message, Long waitForMs) {
        super(message);
        this.waitForMs = waitForMs;
    }

    public Long getWaitInSec() {
        return this.waitForMs/1000L;
    }
}
