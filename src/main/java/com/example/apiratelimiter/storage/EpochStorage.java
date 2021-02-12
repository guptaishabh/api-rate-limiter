package com.example.apiratelimiter.storage;

import java.util.SortedSet;

public interface EpochStorage extends RateLimitStorage<SortedSet<String>> {}