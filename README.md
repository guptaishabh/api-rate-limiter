# api-rate-limiter

This is a Basic Sliding Window API Rate Limiter with In-Memory Implementation.
It stores all the epochs as sorted set.

For Distributed/Centralised Storage, We can use Redis as Distributed Storage.
Implementation can be provided by extending EpochStorage class.

As this is a Basic Sliding Window Rate Limiter.
For fixed Window Rate Limiter, we can take help of Usage class under storage.model to keep the count per duration epoch.

Storing all Epoch Seconds can be pretty memory intensive. We can use a Hybrid Approach by keeping counters for small interval
and implementation can be provided using extension of RateLimitStorage class.
