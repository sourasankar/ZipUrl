package com.zipurl.core.service;

public interface RedisWriteService {

    Boolean saveUrlWithExpiry(String key, String value);
}
