package com.zipurl.core.service.impl;

import com.zipurl.core.dao.ShortUrlRepo;
import com.zipurl.core.service.DatabaseService;
import com.zipurl.core.service.RedisService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final DatabaseService databaseService;

    @CircuitBreaker(name = "redisError", fallbackMethod = "redisFallBackToDB")
    @Override
    public String getCachedUrl(String key) {
        Object url = redisTemplate.opsForValue().get(key);
        if (url != null) {
            return url.toString();
        }
        return getFromDBAndCacheToRedis(key);
    }

    private String redisFallBackToDB(String key, Throwable e) {
        System.out.println("Exception : " + e);
        return getFromDBAndCacheToRedis(key);
    }

    @CircuitBreaker(name = "redisError", fallbackMethod = "redisSaveFailure")
    private Boolean saveUrlWithExpiry(String key, String value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(10));
        return true;
    }

    private Boolean redisSaveFailure(String key, String value, Throwable e) {
        System.out.println("Exception : " + e);
        return false;
    }

    private String getFromDBAndCacheToRedis(String key) {
        String url = databaseService.getFromDB(key);
        if (url != null) {
            Boolean redisFlag = saveUrlWithExpiry(key, url);
            System.out.println("Redis save status : " + redisFlag);
            return url;
        }
        return null;
    }
}