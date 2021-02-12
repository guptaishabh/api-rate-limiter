# Api-rate-limiter

#### Design


This is a Basic Sliding Window API Rate Limiter with In-Memory Implementation using Spring Boot.
It stores all the epochs as sorted set.

For Distributed/Centralised Storage, We can use Redis as Distributed Storage.
Implementation can be provided by extending EpochStorage class.

As this is a Basic Sliding Window Rate Limiter.
For fixed Window Rate Limiter, we can take help of Usage class under storage.model to keep the count per duration epoch.

Storing all Epoch Seconds can be pretty memory intensive. We can use a Hybrid Approach by keeping counters for small interval
and implementation can be provided using extension of RateLimitStorage class.

How to Test:
-
Run Main class **ApiRateLimiterApplication**.
It exposes an Endpoint with default config limit of 100 per Hour.
- GET [http://localhost:8080/rate-limit/test](http://localhost:8080/rate-limit/test)

If the request is fine, it will return 200 "OK". 
Otherwise, return 429 Status Code with *Text Rate limit exceeded. Try again in xx seconds* and an additional Header *x-retry-after* with value in seconds.

#####How to change config limit:
From [RateLimitConfigReader](https://github.com/guptaishabh/api-rate-limiter/blob/main/src/main/java/com/example/apiratelimiter/config/RateLimitConfigReader.java)

Rate Limiting per User/IP:
-
- Rate Limits using client-id + route for authenticated user/api.
- Otherwise rate limits on the caller remote address.