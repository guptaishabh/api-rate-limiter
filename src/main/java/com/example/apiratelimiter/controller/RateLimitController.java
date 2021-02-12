package com.example.apiratelimiter.controller;

import com.example.apiratelimiter.exception.RateLimitExceededException;
import com.example.apiratelimiter.service.IRateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
@RequestMapping("rate-limit")
@Slf4j
public class RateLimitController {

    private final IRateLimitService rateLimitService;

    @Autowired
    public RateLimitController(IRateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    private String getKey(MultiValueMap<String, String> headers,
                          HttpServletRequest request) {
        return headers.getOrDefault("client-id", Collections.emptyList())
                .stream().findFirst().orElseGet(request::getRemoteAddr);
    }

    @GetMapping("test")
    @ResponseBody
    public ResponseEntity test(@RequestHeader MultiValueMap<String, String> headers,
                               HttpServletRequest request) {

        try {
            String userKey = getKey(headers, request);
            rateLimitService.call(userKey, "/rate-limit/test");
        } catch (RateLimitExceededException e) {
            log.error(String.format("API Rate Limit Exceeded for User: %s", getKey(headers, request)));
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .header("X-Retry-After", e.getWaitInSec().toString())
                    .body(String.format("Rate limit exceeded. Try again in %s seconds", e.getWaitInSec()));
        }

        return ResponseEntity.ok("OK");
    }
}
