package com.zipurl.core.service.impl;

import com.zipurl.core.dao.ShortUrlRepo;
import com.zipurl.core.entity.ShortUrl;
import com.zipurl.core.service.DatabaseService;
import com.zipurl.core.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DatabaseServiceImpl implements DatabaseService {

    private final ShortUrlRepo shortUrlRepo;
    private final RedisService redisService;

    @Override
    public String getFromDBAndCacheToRedis(String key) {

        ShortUrl result = shortUrlRepo.findById(key).orElse(null);
        if (result != null && !result.getUrl().isBlank()) {
            Boolean redisFlag = redisService.saveUrlWithExpiry(key, result.getUrl());
            System.out.println("Redis save status - " + redisFlag);
            return result.getUrl();
        }
        return null;
    }
}
