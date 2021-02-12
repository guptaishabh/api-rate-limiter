package com.example.apiratelimiter.service;

import com.example.apiratelimiter.config.RateLimitConfig;
import com.example.apiratelimiter.config.RateLimitConfigReader;
import com.example.apiratelimiter.exception.RateLimitExceededException;
import com.example.apiratelimiter.storage.EpochStorage;
import com.example.apiratelimiter.storage.InMemoryRateLimitStorage;
import com.example.apiratelimiter.storage.model.RateLimitRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.SortedSet;

@Service
@Slf4j
public class RateLimitService implements IRateLimitService {

    private final EpochStorage rateLimitStorage;
    private final RateLimitConfigReader configReader;
    private static final Clock clock = Clock.systemDefaultZone();

    @Autowired
    public RateLimitService(EpochStorage rateLimitStorage, RateLimitConfigReader configReader) {
        this.rateLimitStorage = rateLimitStorage;
        this.configReader = configReader;
    }


    @Override
    public void call(String userKey, String resourceName) throws RateLimitExceededException {
        RateLimitConfig config = configReader.read(resourceName);
        Instant now = Instant.now(clock);

        RateLimitRequest req = new RateLimitRequest(now, resourceName + "-" + userKey, config.getCapacity(),
                config.getExpiration());

        SortedSet<Long> usages = rateLimitStorage.addAndGetWithinLimit(req);

        if (usages.size() > config.getCapacity()) {
            rateLimitStorage.remove(req);
            Long firstUsageInWindow = usages.last();
            Long waitFor = firstUsageInWindow + config.getExpiration().toMillis() - now.toEpochMilli();

            throw new RateLimitExceededException(String.format("Rate Limit Exceeded for %s", userKey), waitFor);
        }
    }
}
