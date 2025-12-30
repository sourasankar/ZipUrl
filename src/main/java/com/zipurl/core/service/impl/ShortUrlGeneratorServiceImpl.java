package com.zipurl.core.service.impl;

import com.fasterxml.uuid.Generators;
import com.zipurl.core.config.ZipUrlConfig;
import com.zipurl.core.service.DatabaseService;
import com.zipurl.core.service.ShortUrlGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ShortUrlGeneratorServiceImpl implements ShortUrlGeneratorService {

    private final ZipUrlConfig zipUrlConfig;
    private final DatabaseService databaseService;
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private String generateShortId() {
        UUID uuid = Generators.timeBasedGenerator().generate();
        long value = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
        value = Math.abs(value);

        return toBase62(value);
    }

    private String toBase62(long value) {
        Integer shortIdSize = zipUrlConfig.getShortIdSize();
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            sb.append(BASE62.charAt((int) (value % 62)));
            value /= 62;
        }
        while (sb.length() < shortIdSize) {
            sb.append('0');
        }

        return sb.reverse().substring(0, shortIdSize);
    }

    @Override
    public String generateShortUrl(String longUrl) {

        String shortId = generateShortId();
        Boolean flag = databaseService.saveToDBAndCacheToRedis(shortId, longUrl);
        if (!flag) return null;

        return zipUrlConfig.getUrlPrefix() + shortId;
    }

}
