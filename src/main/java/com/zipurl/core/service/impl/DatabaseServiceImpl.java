package com.zipurl.core.service.impl;

import com.zipurl.core.dao.ShortUrlRepo;
import com.zipurl.core.entity.ShortUrl;
import com.zipurl.core.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DatabaseServiceImpl implements DatabaseService {

    private final ShortUrlRepo shortUrlRepo;
    private final RedisWriteServiceImpl redisWriteService;

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

    @Override
    public Boolean saveToDBAndCacheToRedis(String shortId, String longUrl) {
        System.out.println("Inside saveToDBAndCacheToRedis");
        ShortUrl entity = new ShortUrl();
        entity.setShortId(shortId);
        entity.setUrl(longUrl);
        try {
            shortUrlRepo.saveAndFlush(entity);
            Boolean redisFlag = redisWriteService.saveUrlWithExpiry(shortId, longUrl);
            System.out.println("saveToDB Redis save status : " + redisFlag);
        } catch (Exception e) {
            System.out.println("DB save failure : " + e);
            return false;
        }
        return true;
    }
}
