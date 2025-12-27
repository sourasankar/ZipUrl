package com.zipurl.core.service;

public interface DatabaseService {

    String getFromDBAndCacheToRedis(String key);
}