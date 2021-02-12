package com.example.apiratelimiter.storage;

import com.example.apiratelimiter.storage.model.RateLimitKeyUtils;
import com.example.apiratelimiter.storage.model.RateLimitRequest;
import com.example.apiratelimiter.storage.model.StorageRateLimitKey;
import com.example.apiratelimiter.storage.model.Usage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class InMemoryRateLimitStorage implements EpochStorage {

    private Map<StorageRateLimitKey, SortedSet<Long>> cache;
    private Clock clock = Clock.systemDefaultZone();

    public InMemoryRateLimitStorage() {
        this.cache = new HashMap<>();
    }

    /**
     * Increments the counter in Epoch bucket and returns usage set
     * @param rateLimitRequest
     * @return
     */
    @Override
    public SortedSet<Long> addAndGetWithinLimit(RateLimitRequest rateLimitRequest) {

        StorageRateLimitKey limitKey = StorageRateLimitKey.fromRequest(rateLimitRequest);

        SortedSet<Long> usages = cache.computeIfAbsent(limitKey, (key) -> new ConcurrentSkipListSet<>(Comparator.reverseOrder()));
        removeExpired(limitKey);

        if(usages.size() <= rateLimitRequest.getCapacity())
            usages.add(rateLimitRequest.getEventTimestamp().toEpochMilli());

        return usages;
    }


    private void removeExpired(StorageRateLimitKey limitKey) {
        Instant now = Instant.now(clock);
        Long limit = now.minusMillis(limitKey.getExpiration().toMillis()).toEpochMilli();

        cache.get(limitKey).removeIf(u -> u < limit);
    }

    @Override
    public void close() throws Exception { }

    @Override
    public void remove(RateLimitRequest request) {
        StorageRateLimitKey limitKey = StorageRateLimitKey.fromRequest(request);
        if(cache.containsKey(limitKey)) {
            cache.get(limitKey).remove(request.getEventTimestamp().toEpochMilli());
        }
    }
}
