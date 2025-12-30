package com.zipurl.core.service.impl;

import com.zipurl.core.config.ZipUrlConfig;
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
    private final ZipUrlConfig zipUrlConfig;

    @CircuitBreaker(name = "redisError", fallbackMethod = "redisSaveFailure")
    @Override
    public Boolean saveUrlWithExpiry(String key, String value) {
        System.out.println("Inside saveUrlWithExpiry");
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(zipUrlConfig.getRedisTtl()));
        return true;
    }

    public Boolean redisSaveFailure(String key, String value, Throwable e) {
        System.out.println("Exception : key - " + key + " value - " + value + " : " + e);
        return false;
    }

}