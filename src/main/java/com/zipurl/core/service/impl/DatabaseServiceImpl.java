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

    @Override
    public String getFromDB(String key) {
        System.out.println("Inside getFromDB");
        ShortUrl result = shortUrlRepo.findById(key).orElse(null);
        if (result != null && !result.getUrl().isBlank()) {
            return result.getUrl();
        }
        return null;
    }

    @Override
    public Boolean saveToDB(String shortId, String longUrl) {
        System.out.println("Inside getFromDB");
        ShortUrl entity = new ShortUrl();
        entity.setShortId(shortId);
        entity.setUrl(longUrl);
        try {
            shortUrlRepo.saveAndFlush(entity);
        } catch (Exception e) {
            System.out.println("DB save failure : " + e);
            return false;
        }
        return true;
    }
}
