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
    public String getCachedUrl(String key) {
        System.out.println("Inside getCachedUrl");
        String url = redisTemplate.opsForValue().get(key);
        if (url != null) {
            return url;
        }
        System.out.println("Url not found Redis");
        return getFromDBAndCacheToRedis(key);
    }

    private String redisFallBackToDB(String key, Throwable e) {
        System.out.println("Exception : " + e);
        return getFromDBAndCacheToRedis(key);
    }

    private String getFromDBAndCacheToRedis(String key) {
        System.out.println("Inside getFromDBAndCacheToRedis");
        String url = databaseService.getFromDB(key);
        if (url != null) {
            Boolean redisFlag = redisWriteService.saveUrlWithExpiry(key, url);
            System.out.println("Redis save status : " + redisFlag);
            return url;
        }
        System.out.println("Url not found DB");
        return null;
    }
}