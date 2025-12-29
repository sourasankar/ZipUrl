package com.zipurl.core.service;

public interface DatabaseService {

    String getFromDB(String key);

    Boolean saveToDB(String shortId, String longUrl);
}