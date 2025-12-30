package com.zipurl.core.service.impl;

import com.zipurl.core.service.DatabaseService;
import com.zipurl.core.service.RedisReadService;
import com.zipurl.core.service.RedisWriteService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisReadServiceImpl implements RedisReadService {

    private final RedisTemplate<String, String> redisTemplate;
    private final DatabaseService databaseService;
    private final RedisWriteService redisWriteService;

    @CircuitBreaker(name = "redisError", fallbackMethod = "redisFallBackToDB")
    @Override
    public String getCachedUrl(String shortId) {
        System.out.println("Inside getRedisCachedUrl");
        String longUrl = redisTemplate.opsForValue().get(shortId);
        if (longUrl != null) {
            return longUrl;
        }
        System.out.println("Url not found Redis");
        return getFromDB(shortId);
    }

    public String redisFallBackToDB(String shortId, Throwable e) {
        System.out.println("Exception : " + e);
        return getFromDB(shortId);
    }

    private String getFromDB(String shortId) {
        System.out.println("Inside getFromDB");
        return databaseService.getFromDBAndCacheToRedis(shortId);
    }
}