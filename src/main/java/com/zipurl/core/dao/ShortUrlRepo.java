package com.zipurl.core.dao;

import com.zipurl.core.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepo extends JpaRepository<ShortUrl, String> {
}
