package com.zipurl.core.service;

public interface RedisReadService {

    String getCachedUrl(String key);
}
