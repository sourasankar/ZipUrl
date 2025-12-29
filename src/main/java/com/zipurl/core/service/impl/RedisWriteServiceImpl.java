package com.zipurl.core.service.impl;

import com.zipurl.core.service.DatabaseService;
import com.zipurl.core.service.RedisWriteService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisWriteServiceImpl implements RedisWriteService {

    private final RedisTemplate<String, String> redisTemplate;
    private final DatabaseService databaseService;

    @CircuitBreaker(name = "redisError", fallbackMethod = "redisSaveFailure")
    @Override
    public Boolean saveUrlWithExpiry(String key, String value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(10));
        return true;
    }

    public Boolean redisSaveFailure(String key, String value, Throwable e) {
        System.out.println("Exception : key - " + key + " value - " + value + " : " + e);
        return false;
    }

}