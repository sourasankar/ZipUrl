package com.zipurl.core.service.impl;

import com.zipurl.core.dao.ShortUrlRepo;
import com.zipurl.core.entity.ShortUrl;
import com.zipurl.core.service.DatabaseService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DatabaseServiceImpl implements DatabaseService {

    private final ShortUrlRepo shortUrlRepo;
    private final RedisWriteServiceImpl redisWriteService;

    @CircuitBreaker(name = "dbError", fallbackMethod = "fallbackDBMethod")
    @Override
    public String getFromDBAndCacheToRedis(String key) {
        System.out.println("Inside getFromDBAndCacheToRedis");
        ShortUrl result = shortUrlRepo.findById(key).orElse(null);
        if (result != null && !result.getUrl().isBlank()) {
            String shortId = result.getShortId();
            String longUrl = result.getUrl();
            Boolean redisFlag = redisWriteService.saveUrlWithExpiry(shortId, longUrl);
            System.out.println("getFromDB Redis save status : " + redisFlag);
            return longUrl;
        }
        System.out.println("Url not found DB");
        return null;
    }

    public String fallbackDBMethod(String key, Throwable e) {
        System.out.println("Inside fallbackDBMethod : " + e);
        return null;
    }

    @CircuitBreaker(name = "dbError", fallbackMethod = "fallbackDBMethod")
    @Override
    public Boolean saveToDBAndCacheToRedis(String shortId, String longUrl) {
        System.out.println("Inside saveToDBAndCacheToRedis");
        ShortUrl entity = new ShortUrl();
        entity.setShortId(shortId);
        entity.setUrl(longUrl);
        shortUrlRepo.saveAndFlush(entity);
        Boolean redisFlag = redisWriteService.saveUrlWithExpiry(shortId, longUrl);
        System.out.println("saveToDB Redis save status : " + redisFlag);
        return true;
    }

    public Boolean fallbackDBMethod(String shortId, String longUrl, Throwable e) {
        System.out.println("Inside fallbackDBMethod : " + e);
        return false;
    }

}
