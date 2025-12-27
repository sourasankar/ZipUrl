package com.zipurl.core.service;

public interface RedisService {

    Boolean saveUrlWithExpiry(String key, String value);

    String getCachedUrl(String key);
}
