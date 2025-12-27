package com.zipurl.core.service;

public interface RedisService {

    String getCachedUrl(String key);
}
